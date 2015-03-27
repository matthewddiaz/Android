package com.matthewddiaz.dictionaryapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class DictionaryActivity extends Activity {
   private EditText mTerm;
   private EditText mDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        mTerm = (EditText)findViewById(R.id.inputTerm);
        mDefinition = (EditText)findViewById(R.id.inputDefinition);
    }

    @Override
    public void onPause(){//informs the Application class that an activity at the pause
        super.onPause();//stage does not exist
        DictionaryApplication app = (DictionaryApplication)getApplication();
        app.pause(null);
    }

    @Override
    public void onResume(){
        super.onResume();
        DictionaryApplication app = (DictionaryApplication)getApplication();
        app.resume(this);
    }


    public void getButtonOnClick(View view){//gets a String value from the EditText
        String term = mTerm.getText().toString();//and calls getApplication to access the Application class
        DictionaryApplication app = (DictionaryApplication)getApplication();
        app.startDictionaryThread(false,term);//this method creates new DictionaryThread to perform the task

    }
    public void updateButtonOnClick(View view){
        String term = mTerm.getText().toString();
        String definition = mDefinition.getText().toString();
        DictionaryApplication app = (DictionaryApplication)getApplication();
        app.startDictionaryThread(term,definition);
    }

    public void deleteButtonOnClick(View view){
        String term = mTerm.getText().toString();
        DictionaryApplication app = (DictionaryApplication)getApplication();
        app.startDictionaryThread(true,term);
    }

    public void updateDefinitionOutput(final String definition){//sets outputs the definition of
        mDefinition.setText(definition);//a word if that term was already defined. Used for get button
    }

}
