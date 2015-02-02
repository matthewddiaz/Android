package com.matthewddiaz.namethatcountry;

import android.app.Activity;
import java.util.Arrays;

/**
 * Created by matthew on 2/1/15.
 */
public class Questions extends Activity{
    private CharSequence question;
    private String[] pos_choices;
    private CharSequence answer;

    public Questions(int num){
        int position  = num*4;
        question = getResources().getString(R.string.q_text);
        String[] all_choices = getResources().getStringArray(R.array.answer_choice_list);
        pos_choices = Arrays.copyOfRange(all_choices,position,position+3);
        answer = getResources().getString(R.string.ans1);
    }

    public CharSequence getQuestion(){
        return question;
    }

    public CharSequence get_answer(){
        return answer;
    }

    public CharSequence get_ans_choices(int choice_num){
        return pos_choices[choice_num];
    }

}
