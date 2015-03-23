package com.matthewddiaz.internetpermission;

import android.app.Activity;
import android.content.Context;
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
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    private EditText mInputURL;
    private EditText mInputTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputURL = (EditText) findViewById(R.id.user_url_input);
        mInputTerm = (EditText) findViewById(R.id.user_term_input);
    }

    public void startReaderThread(String user_url_input,String user_term_input){
        ReaderThread mThreadRead = new ReaderThread(user_url_input,user_term_input);//creates new ReaderThread
        mThreadRead.start();
    }

    public void startURLThread(String user_url){
        OpenURLThread mThreadOpenURL = new OpenURLThread(user_url);//creates new OpenURLThread
        mThreadOpenURL.start();
    }

    public void enter_handler(View view){
        String url = mInputURL.getText().toString();//gets user url input from EditText variable
        String term = mInputTerm.getText().toString();//gets user term input
        if(checkValidInput(url,term)){
            errorToast("Please input a URL and a term");
        }else{
            startReaderThread(url,term);
        }
    }

    private void errorToast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast= Toast.makeText(context,text,duration);
        toast.show();

    }

    private boolean checkValidInput(String url,String term){
        return (url.equals("") || term.equals(""));
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
            num = R.drawable.found;//Android treats R variables as int can pass
        }//to makeToast
        else{
            num = R.drawable.not_found;
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
        final boolean safe = activities.size() > 0;//to open link with its given resources
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
        private String term;
        private boolean is_word_there = false;


        public ReaderThread(String user_url,String user_term){
            url = user_url;
            term = user_term;
        }



        /*Inside of the if statement I use Java's Pattern class which works with Java's match pattern
        apparently it is more efficent than using String contains for long texts! .CASE_INSENSITIVE
        means that tiger or Tiger will still show as true!.
        */

        @Override
        public void run(){
            try{
                URL readingWeb = new URL(url);
                try{//This BufferedReader that reads in a URL needs to be in a thread since it clog UI thread!
                    BufferedReader in = new BufferedReader(new InputStreamReader(readingWeb.openStream()));
                    String line;//the if statement below uses matcher class to check if the term is in the url site.
                    while ((line = in.readLine()) != null) {//the .* means that anything can come before or after this word
                        if(line.matches(".*" + term + ".*")){//and then removes the string from the bufferedReader
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
