package com.matthewddiaz.dictionaryapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DictionaryActivity extends Activity {
   private EditText mTerm;
   private EditText mDefinition;
   private DictionaryDAO mDictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        mTerm = (EditText)findViewById(R.id.inputTerm);
        mDefinition = (EditText)findViewById(R.id.inputDefinition);
        mDictionary = new DictionaryDAO(this);
    }

    public void getButtonOnClick(View view){
        String term = mTerm.getText().toString();
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
        mDefinition.setText(definition);//set the definition to the given definition
        if(isWordHere == false){//if the term is not in the database then Term is not defined
            toastMessage("Term is not defined");//is displayed
        }
    }
    public void updateButtonOnClick(View view){
        String term = mTerm.getText().toString();
        String definition = mDefinition.getText().toString();
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
        //if the word is to in the database it means that it's a new term
        if(isWordHere == false){//for new terms the user needs to input a term with a definition
            CharSequence text = "";//those the fuction checkingValudAddition checks for the requirements
            boolean isValid = checkingValidAddition(term,definition);
            if(isValid == true){
                mDictionary.createDictionaryEntry(term,definition);
                text = "Term was added successfully";
            }
            else{
                text = "Error term was not added";
            }
            toastMessage(text);
        }
        else{//if Term already exists then a update is done instead
            mDictionary.updateDictionaryEntry(term,definition);
            toastMessage("Term was updated successfully");
        }
    }

    public void deleteButtonOnClick(View view){
        String term = mTerm.getText().toString();
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
        String text = "";
        if(isWordHere == true){//if true then delete the Term and its definition from the database
           mDictionary.deleteDictionaryEntry(term);
           text = "Term was deleted successfully";
        }
        else{//if the word is not in the database display a toast stating that the term was not
            text = "Term is not defined";//defined before and so couldn't be deleted
        }
        toastMessage(text);
    }

    private boolean checkingValidAddition(String term,String definition){
        if(term.equals("") || definition.equals("")){
            return false;
        }
        return true;
    }

    private void toastMessage(CharSequence text){//method that creates toast accepts a string
        Context context = getApplicationContext();//that will output the message
        int duration = Toast.LENGTH_SHORT;//length that the toast displays for
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }

}
