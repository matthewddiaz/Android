package com.matthewddiaz.openandclosefiles;

import android.app.Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by matthew on 3/5/15.
 */
public class MyApplication extends Application {
    private MainActivity currentActivity = null;
    private CounterThread mCounterThread = null;
    private MakingFileThread mFileThread;


    public void onCreate(){
        super.onCreate();
    }

    public void resume(MainActivity update){
        currentActivity = update;
        if(mCounterThread != null){
            mCounterThread.onResume();
        }
    }

    public void pause(MainActivity update){
        currentActivity = update;
        if(mCounterThread != null){
            mCounterThread.onPause();
        }
    }

    public void MakeFileThread(String s1,String s2){
        mFileThread = new MakingFileThread(s1,s2);
    }

    public void startCounterThread(){
        mCounterThread = new CounterThread();
        mCounterThread.start();
    }

    public void updateSeconds(final int seconds){
        Runnable UIdoWork = new Runnable(){
            public void run(){
                if(currentActivity != null){
                    currentActivity.updateCountDown(seconds);
                }
            }
        };
        if(currentActivity != null){
            currentActivity.runOnUiThread(UIdoWork);
        }
    }
    /*Anything that changes View has to be made by the UI thread(Main thread). Since the
    MakingFileThread called updateAnswers you have to use RunOnUIThread or else the MakingFileThread
    would execute the line mText.setText(ans) which causes the program to crash! You can't modify
    views from non-UI thread aka (Main Thread) .*/
    public void updateWriteFile(final String userText){
        Runnable UIdoWork = new Runnable(){
            public void run(){
                if(currentActivity != null){
                    currentActivity.updateFileOutput(userText);
                }
            }
        };
        if(currentActivity != null){
            currentActivity.runOnUiThread(UIdoWork);
        }
    }


    private class CounterThread extends Thread{
        private int count = 10;
        private final Object lock = new Object();//creating an object to  synchronize on!
        private volatile boolean isRunning = true;

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
                        lock.wait();//while count is greater than 10 the program will check if
                    }catch(InterruptedException e){//isRunning is set to false via onPause()
                        e.printStackTrace();//when this is true will cause the CounterThread to wait
                    }//until onResume() which calls the notify() and therefore allows the run()
                }//to finish its task!

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
