package com.matthewddiaz.namethatcountry;

import android.content.Intent;
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

    /*Created Buttons so that Opener.java would connect with
    opener_question.xml and so that onClick handler works!
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opener_question);
        mContitent1 = (Button)findViewById(R.id.option_1);
        mContitent2 = (Button)findViewById(R.id.option_2);
        mContitent3 = (Button)findViewById(R.id.option_3);
        mContitent4 = (Button)findViewById(R.id.option_4);
    }

    /*The Intent allows for another activity to start!
    *The method putExtra allows for the current activity
    *to send along information to the activity being called
    * which is Countries.java. Stores values of R's that are
    * unique to each of those variables
    * */

     public void option1_handler(View view){
        Intent intent = new Intent(this, Countries.class);
        int[] south_america_ids= new int[3];
        south_america_ids[0] = R.drawable.south_america;
        south_america_ids[1] = R.array.south_america_list;
        south_america_ids[2] = R.array.south_america_answer_list;
        intent.putExtra("continent",south_america_ids);
        startActivity(intent);
    }

    public void option2_handler(View view){
        Intent intent = new Intent(this, Countries.class);
        int[] europe_ids= new int[3];
        europe_ids[0] = R.drawable.europe_map;
        europe_ids[1] = R.array.europe_list;
        europe_ids[2] = R.array.europe_answers_list;
        intent.putExtra("continent",europe_ids);
        startActivity(intent);
    }
    public void option3_handler(View view){

    }
    public void option4_handler(View view){

    }
}
