package com.matthewddiaz.dictionaryapp;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

/**
 * Created by matthew on 3/27/15.
 */
public class DictionaryApplication extends Application {
    private DictionaryActivity currentActivity = null;//The Activity does not exist at the start.
    private DictionaryThread mDictionaryThread;
    private DictionaryDAO mDictionary;

    public void onCreate(){
        super.onCreate();
        mDictionary = new DictionaryDAO(this);
    }

    public void resume(DictionaryActivity update){//if the activity is visible then the activity is
        currentActivity = update;//updated to the current one which DicitonaryActivity passes as
        if(mDictionaryThread != null){//a parameter when onResume is clicked will set currentActivity
            mDictionaryThread.onResume();//equal to the passed in value
        }
    }

    public void pause(DictionaryActivity update){
        currentActivity = update;
        if(mDictionaryThread != null){
            mDictionaryThread.onPause();
        }
    }

    //this fuction creates a new DictionaryThread and little thread with .start()
    public void startDictionaryThread(boolean userChoice,String term){
        mDictionaryThread = new DictionaryThread(userChoice,term);
        mDictionaryThread.start();//the constructor is only for get and delete
    }

    public void startDictionaryThread(String term,String definition){
        mDictionaryThread = new DictionaryThread(term,definition);
        mDictionaryThread.start();//the constructor is only for update
    }

    private void getTerm(final String term){
        Cursor cursor = mDictionary.fetchAllEntries();//creating a cursor that will iterate through
        boolean isWordHere = false;//the sqlite database
        String definition = "";
        while(!cursor.isAfterLast()){//the parameter for getColumnIndex has to be a name of one of the
            String word = cursor.getString(cursor.getColumnIndex("term"));//columns in the actual database
            if(word.equals(term)){//searching for the term that was input
                isWordHere = true;
                definition = cursor.getString(cursor.getColumnIndex("definition"));
                break;//if true then get the definition from the database
            }
            cursor.moveToNext();
        }

        final String def = definition;//these two variables where created since the values
        final boolean inDictionary = isWordHere;//that run in the Runnable need to be final

        Runnable UIdoWork = new Runnable(){
            @Override
            public void run(){
                currentActivity.updateDefinitionOutput(def);//set the definition to the given definition
                if(!inDictionary){//if the term is not in the database then Term is not defined
                    toastMessage(term + " is not defined");//is displayed
                }
            }
        };
        if(currentActivity != null){
            currentActivity.runOnUiThread(UIdoWork);
        }
    }

    private void updateTerm(String term,String definition){
        Cursor cursor = mDictionary.fetchAllEntries();
        boolean isWordHere = false;
        while(!cursor.isAfterLast()) {
            String word = cursor.getString(cursor.getColumnIndex("term"));
            if(term.equals(word) ){//checking if the word that is input is in the database
                isWordHere = true;
                break;
            }
            cursor.moveToNext();
        }
        //if the word is not in the database it means that it's a new term
        String message = "";
        if(!isWordHere){//for new terms the user needs to input a term with a definition
            CharSequence text;//those the fuction checkingValudAddition checks for the requirements
            boolean isValid = checkingValidAddition(term,definition);
            if(!isValid){
                mDictionary.createDictionaryEntry(term,definition);
                message = term + " was added successfully";
            }
            else{
                message = "Error: " +  term + " was not added";
            }
        }
        else{//if Term already exists then a update is done instead
            mDictionary.updateDictionaryEntry(term,definition);
            message = term + " was updated successfully";
        }
        final String actionMessage = message;
        Runnable UIdoWork = new Runnable(){
            @Override
            public void run(){
                toastMessage(actionMessage);
            }
        };
        currentActivity.runOnUiThread(UIdoWork);
    }

    private void deleteTerm(String term){
        Cursor cursor = mDictionary.fetchAllEntries();
        boolean isWordHere = false;
        while(!cursor.isAfterLast()){
            String word = cursor.getString(cursor.getColumnIndex("term"));
            if(word.equals(term)){//checks if term is in the database
                isWordHere = true;
                break;
            }
            cursor.moveToNext();
        }
        String text;
        if(isWordHere){//if true then delete the Term and its definition from the database
            mDictionary.deleteDictionaryEntry(term);
            text = term + " was deleted successfully";
        }
        else{//if the word is not in the database display a toast stating that the term was not
            text = term + " is not defined";//defined before and so couldn't be deleted
        }
        final String message = text;
        Runnable UIdoWork = new Runnable(){
            @Override
            public void run(){
                toastMessage(message);
            }
        };
        currentActivity.runOnUiThread(UIdoWork);
    }

    private boolean checkingValidAddition(String term,String definition){
        return (term.equals("") || definition.equals(""));
    }

    private void toastMessage(CharSequence text){//method that creates toast accepts a string
        Context context = getApplicationContext();//that will output the message
        int duration = Toast.LENGTH_SHORT;//length that the toast displays for
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }


    private class DictionaryThread extends Thread{
        private boolean remove_true;
        private volatile  boolean isRunning = true;
        private final Object lock = new Object();
        private String term;
        private String definition;


        public void onResume(){//allows the thread to continue working when the activity comes back
            if(!isRunning){
                isRunning = true;
            }
            synchronized (lock){
                lock.notify();
            }
        }

        public void onPause(){
            isRunning = false;
        }//will cause the thread to pause until the
        //activity comes back

        //this constructor is called when the update method is used.
        public DictionaryThread(boolean userChoice,String term){
            remove_true = userChoice;
            this.term = term;
        }

        public DictionaryThread(String term,String definition){
            this.term = term;
            this.definition = definition;
        }


        @Override
        public void run(){
            synchronized (lock) {//checks if the app is on pause state
                if(!isRunning)try{
                    lock.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            if (definition != null) {
                updateTerm(term, definition);
            } else if (remove_true) {
                deleteTerm(term);
            } else {
                getTerm(term);
            }
        }
    }


}
