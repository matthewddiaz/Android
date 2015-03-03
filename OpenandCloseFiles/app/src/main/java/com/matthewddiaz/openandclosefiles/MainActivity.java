package com.matthewddiaz.openandclosefiles;

import android.app.Activity;
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
    private CounterThread mCounterThread;
    private MakingFileThread mFileThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.text);
        mUserInput = (EditText)findViewById(R.id.userInput);
    }

    public void startCounterThread(){
        mCounterThread = new CounterThread();
        mCounterThread.start();
    }

    public void startMakingFileThread(String s1,String s2){
        mFileThread = new MakingFileThread(s1,s2);
        mFileThread.start();
    }

    public void button_handler(View v){
        String val = mUserInput.getText().toString();
        startCounterThread();
        String file = "myfile.txt";
        startMakingFileThread(val,file);
       }

    public void updateSeconds(final long seconds){
        Runnable UIdoWork = new Runnable(){/*need to but anything that changes view make UI thread work.
            since the Worker thread called updateAnswers with RunOnUIThread, the worker
            thread would be the one executing the code mText.setText(ans) which causes the
            program to crash!
            You can't modify views from non-UI thread.*/
            public void run(){
                String time = String.valueOf(seconds);
                mText.setText("Your file will open in " + time + " seconds");
            }
        };
        runOnUiThread(UIdoWork);
    }


    public void updateWriteFile(final String userText){
        Runnable UIdoWork = new Runnable(){
            public void run(){
                mText.setText(userText);
            }
        };
        runOnUiThread(UIdoWork);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CounterThread extends Thread{
        int count;
        public void run(){
            count = 10;
            while(count != 0){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                updateSeconds(count--);
            }
        }
    }

    private class MakingFileThread extends Thread{
        String userInput;
        String fileName;

        public MakingFileThread(String s1,String s2){
            userInput = s1;
            fileName = s2;
        }

        @Override
        public void run(){

            try{
                sleep(11000);//will make this thread wait 11 seconds until it actually does its work!
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream outputStream = openFileOutput(fileName,MODE_PRIVATE);
                BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(outputStream,"UTF-8")));
                writer.write(userInput);
                writer.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try {
                FileInputStream fis = openFileInput( fileName );
                BufferedReader reader = new BufferedReader((new InputStreamReader(fis,"UTF-8")));
                String ans = reader.readLine();
                updateWriteFile(ans);
                reader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
