package com.matthewddiaz.internetpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    private TextView mInputVal;
    private ReaderThread mThreadRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputVal = (TextView) findViewById(R.id.user_text);
    }

    public void startReaderThread(String user_input){
        mThreadRead = new ReaderThread(user_input);
        mThreadRead.start();
    }


    public void enter_handler(View view){
        String url = mInputVal.getText().toString();
        startReaderThread(url);
    }

    public void updateAnswer(boolean ans){
        final String text;
        if(ans == true){
            text = "true!";
        }
        else{
            text = "false";
        }
        Runnable UIdoWork = new Runnable() {
            @Override
            public void run() {
                makeToast(text);
            }
        };
        runOnUiThread(UIdoWork);
    }


    public void makeToast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }

    public class ReaderThread extends Thread{
        private String url;
        private boolean is_word_there = false;


        public ReaderThread(String user_url){
            url = user_url;
        }

        @Override
        public void run(){
            try{
                URL readingWeb = new URL(url);
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(readingWeb.openStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        if(line.contains("tiger")){
                            is_word_there = true;
                            break;
                        }
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            updateAnswer(is_word_there);
        }
    }

}
