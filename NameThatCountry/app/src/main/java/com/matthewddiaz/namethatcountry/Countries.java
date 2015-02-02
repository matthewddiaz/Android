package com.matthewddiaz.namethatcountry;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Countries extends ActionBarActivity {
    TextView mQuestion;
    Button mChoice1;
    Button mChoice2;
    Button mChoice3;
    Button mChoice4;
    Button mNext;
    Questions[] mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        mQuestion = (TextView)findViewById(R.id.question);
        mChoice1 = (Button)findViewById(R.id.choice1);
        mChoice2 = (Button)findViewById(R.id.choice2);
        mChoice3 = (Button)findViewById(R.id.choice3);
        mChoice4 = (Button)findViewById(R.id.choice4);
        mNext = (Button)findViewById(R.id.button_next);
        mList = new Questions[10];
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countries, menu);
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

    public void createListOfPatterns(){
        for(int x = 0; x < 2;x++){
            mList[x] = new Questions(x);
        }
    }

    public void Choice1_handler(View view){
        CharSequence ans = mChoice1.getText();
        is_Correct(ans);
    }

    public void Choice2_handler(View view){
        CharSequence ans = mChoice2.getText();
        is_Correct(ans);
    }

    public void Choice3_handler(View view){
        CharSequence ans = mChoice3.getText();
        is_Correct(ans);
    }

    public void Choice4_handler(View view){
        CharSequence ans = mChoice4.getText();
        is_Correct(ans);
    }

    public void next_handler(View view){

    }

    private void is_Correct(CharSequence user_ans){
        if(user_ans == getResources().getString(R.string.c_ans1)){
            make_Toast(R.drawable.rsz_correct);
        }
        else
            make_Toast(R.drawable.rsz_false1);
    }

    private void make_Toast(int imageNum){
        Toast toast = new Toast(this);
        ImageView view = new ImageView(this);
        view.setImageResource(imageNum);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();
    }
}
