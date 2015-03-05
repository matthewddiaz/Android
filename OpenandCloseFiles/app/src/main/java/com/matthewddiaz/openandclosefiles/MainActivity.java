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
        mCounterThread = new CounterThread();
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mCounterThread != null){//should only pause mCounterThread if it exists!
            mCounterThread.onPause();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mCounterThread != null){//should only resume mCounterThread if it exists!
            mCounterThread.onResume();
        }
    }

    public void startCounterThread(){
        mCounterThread.start();
    }

    public void MakeFileThread(String s1,String s2){
        mFileThread = new MakingFileThread(s1,s2);
    }

    public void button_handler(View v){
        String val = mUserInput.getText().toString();
        if(mCounterThread == null){
            mCounterThread = new CounterThread();
        }
        String file = "myfile.txt";
        MakeFileThread(val, file);
        startCounterThread();
    }

    /*Anything that changes View has to be made by the UI thread(Main thread). Since the Worker
     thread called updateAnswers you have to use RunOnUIThread or else the worker thread would
     execute the line mText.setText(ans) which causes the program to crash! You can't modify views
     from non-UI thread aka (Main Thread) .*/
    public void updateSeconds(final long seconds){
        Runnable UIdoWork = new Runnable(){
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

    private class CounterThread extends Thread{
        private int count = 10;
        private final Object lock = new Object();//creating an object to  synchronize on!
        private volatile boolean isRunning = true;
        /*"volatile keyword is also used to communicate content of memory between threads."
        lock is synchronized inside of both onResume and run method! If isRunning boolean variable
         turns false due to onPause() then inside of the synchronized block the program will run
         until it reaches the line lock.wait() where it releases the lock and also waits until
         notify() is called. notify() is called when onResume() method is called setting isRunning
         to true first and then performing lock.notify() which also lock.wait() to end and the thread
         to continue performing its task!
         */
        public void onResume(){
            if(!isRunning){
                isRunning = true;
            }
            synchronized (lock){
                lock.notify();
            }
        }

        public void onPause(){
            isRunning = false;
        }


        @Override
        public synchronized void run(){
            while(count != 0){
                synchronized (lock){
                    if(!isRunning)try{
                        lock.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                updateSeconds(count--);
             }
            mFileThread.start();//once the countdown is complete start mFileThread
            mCounterThread = null;/*once the Thread is complete I set this thread to null
            so that when I press the handler again a new open will get created!
            */
        }
    }

    private class MakingFileThread extends Thread{
        private String userInput;
        private String fileName;
        public MakingFileThread(String s1,String s2){
            userInput = s1;
            fileName = s2;
        }

        @Override
        public void run(){

            try{
                sleep(1000);//will make this thread wait 1 seconds after mCounterThread is done!
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
