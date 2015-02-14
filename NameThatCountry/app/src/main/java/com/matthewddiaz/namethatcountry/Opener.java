package com.matthewddiaz.namethatcountry;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by matthew on 2/13/15.
 */
public class Opener extends ActionBarActivity {
    private Button mContitent1;
    private Button mContitent2;
    private Button mContitent3;
    private Button mContitent4;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opener_question);
        mContitent1 = (Button)findViewById(R.id.option_1);
        mContitent2 = (Button)findViewById(R.id.option_2);
        mContitent3 = (Button)findViewById(R.id.option_3);
        mContitent4 = (Button)findViewById(R.id.option_4);
    }

    public void option1_handler(View view){

    }

    public void option2_handler(View view){

    }
    public void option3_handler(View view){

    }
    public void option4_handler(View view){

    }
}
