package com.matthewddiaz.namethatcountry;

import android.app.Activity;
import android.os.Bundle;

import java.util.Arrays;
import java.lang.Object;

/**
 * Created by matthew on 2/1/15.
 */
public class Questions extends Activity{
    private String mQuestion;
    private String mAnswer;
    private String[] mChoices;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
    }



    public Questions(String q,String a,String[] c){
       mQuestion = q;
       mAnswer = a;
       mChoices = new String[4];//this basically adds ths three wrong answers
       System.arraycopy(c,0,mChoices,0,c.length); // with the correct to make 4 possible choices
       mChoices[3] = a;
       shuffling();
    }

    public String getQuestion(){
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public String getChoices(int choice){
        return mChoices[choice];
    }

    public void shuffling(){
        ShuffleString s1 = new ShuffleString(mChoices);//The shuffle should only
        mChoices = s1.shuffle();//be done once per Question object!!
    }


}
