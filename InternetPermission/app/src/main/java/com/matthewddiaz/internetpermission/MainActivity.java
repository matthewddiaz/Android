package com.matthewddiaz.internetpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
        final int num;
        if(ans == true){
            num = R.drawable.jesusagrees;//Android treats R variables as int can pass
        }//to makeToast
        else{
            num = R.drawable.dontdoit;
        }
        Runnable UIdoWork = new Runnable() {
            @Override
            public void run() {
                makeToast(num);
            }
        };
        runOnUiThread(UIdoWork);
    }


    public void makeToast(int imageNum){//making a toast that displays an image which is stored
        Toast toast  = new Toast(this);//in drawable file
        ImageView view = new ImageView(this);//Have to create an Image View
        view.setImageResource(imageNum);//set the view to the image
        toast.setView(view);
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
                try{//This BufferedReader that reads in a URL needs to be in a thread since it clog UI thread!
                    BufferedReader in = new BufferedReader(new InputStreamReader(readingWeb.openStream()));
                    String line;
                    while ((line = in.readLine()) != null) {//readLine gets a string of the line
                        if(line.contains("tiger")){//and then removes the string from the bufferedReader
                            is_word_there = true;//while do this until no more string lines are left
                            break;
                        }//contains is a string method that checks if that certain word is there or not!
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
