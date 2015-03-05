package com.matthewddiaz.openandclosefiles;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {
    private TextView mText;
    private EditText mUserInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.text);
        mUserInput = (EditText)findViewById(R.id.userInput);
    }

    @Override
    public void onPause(){
        super.onPause();
        MyApplication app = (MyApplication)getApplication();
        app.pause(null);
    }

    @Override
    public void onResume(){
        super.onResume();
        MyApplication app = (MyApplication)getApplication();
        app.resume(this);
    }

    public void button_handler(View v){
        String val = mUserInput.getText().toString();
        String file = "myfile.txt";
        MyApplication app = (MyApplication)getApplication();
        app.MakeFileThread(val,file);
        app.startCounterThread();
    }

    public void updateCountDown(final int seconds){
        String secondText = String.valueOf(seconds);
        mText.setText(secondText);
    }

    public void updateFileOutput(final String userText){
        mText.setText(userText);
    }

}
