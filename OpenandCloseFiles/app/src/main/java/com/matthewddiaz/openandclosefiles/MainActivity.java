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
    private CounterThread mThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.text);
        mUserInput = (EditText)findViewById(R.id.userInput);
        startThread();
    }

    public void startThread(){
        mThread = new CounterThread();
        mThread.start();
    }

    public void button_handler(View v){
        String val = mUserInput.getText().toString();
        String file = "myfile.txt";
        try {
            FileOutputStream outputStream = openFileOutput(file,MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(outputStream,"UTF-8")));
            writer.write(val);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            FileInputStream fis = openFileInput( file );
            BufferedReader reader = new BufferedReader((new InputStreamReader(fis,"UTF-8")));
            String ans = reader.readLine();
            mText.setText(ans);
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAnswers(final long seconds){
        Runnable UIdoWork = new Runnable(){/*need to but anything that changes view make UI thread work.
            since the Worker thread called updateAnswers with RunOnUIThread, the worker
            thread would be the one executing the code mText.setText(ans) which causes the
            program to crash!
            You can't modify views from non-UI thread.*/
            public void run(){
                String ans = String.valueOf(seconds);
                mText.setText(ans);
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
            count = 0;
            while(count != 10){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                updateAnswers(++count);
            }
        }
    }
}
