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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.text);
        mUserInput = (EditText)findViewById(R.id.userInput);
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
}
