package com.matthewddiaz.internetpermission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity {

    private EditText mInputVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputVal = (EditText) findViewById(R.id.user_text);
    }

    public void startReaderThread(String user_input){
        ReaderThread mThreadRead = new ReaderThread(user_input);//creates new ReaderThread
        mThreadRead.start();
    }

    public void startURLThread(String user_url){
        OpenURLThread mThreadOpenURL = new OpenURLThread(user_url);//creates new OpenURLThread
        mThreadOpenURL.start();
    }

    public void enter_handler(View view){
        String url = mInputVal.getText().toString();//gets user input from EditText variable
        startReaderThread(url);
    }

    public void makeToast(int imageNum){//making a toast that displays an image which is stored
        Toast toast  = new Toast(this);//in drawable file
        ImageView view = new ImageView(this);//Have to create an Image View
        view.setImageResource(imageNum);//set the view to the image
        toast.setView(view);
        toast.show();
    }

    public void updateAnswer(boolean ans,String urlSite){
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
        runOnUiThread(UIdoWork);//runOnUIThread allows the code in the Thread to be run by the
        // UI instead of the thread that called it!

        if(ans == true){//this is to open URL site if word exits!
            startURLThread(urlSite);
        }
    }

    public void makeIntent(Uri site){//steps to open a URL link
        final Intent intent = new Intent(Intent.ACTION_VIEW,site);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent,0);//checking if app is able
        final boolean safe = activities.size() > 0;//to oepn link with its given resources
        Runnable UIdoWork = new Runnable() {
            @Override
            public void run() {
                if(safe){
                startActivity(intent);
                }
            }
        };
        runOnUiThread(UIdoWork);
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
            updateAnswer(is_word_there,url);
        }
    }

    public class OpenURLThread extends Thread{
        private String url;

        public OpenURLThread(String site){
            url = site;
        }

        @Override
        public void run(){
            try{
                sleep(5000);//make thread sleep for 5 seconds before performing task
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            Uri site = Uri.parse(url);//uses the String vale url to convert to a Uri
            makeIntent(site);
        }
    }

}
