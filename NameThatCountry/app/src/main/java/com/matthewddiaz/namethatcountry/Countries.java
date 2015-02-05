package com.matthewddiaz.namethatcountry;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class Countries extends ActionBarActivity {
    TextView mQuestion;
    Button mChoice1;
    Button mChoice2;
    Button mChoice3;
    Button mChoice4;
    Button mNext;
    private String[] questions, answers;
    Questions[] mList;
    int mListLen;
    int mUniversalCounter = 0;
    int[] rand_pos;


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
        questions = getResources().getStringArray(R.array.question_list);
        answers = getResources().getStringArray(R.array.answer_list);
        int len = questions.length;//the length of the array is determined by the total number
        mList = new Questions[len];//of questions that I have available
        mListLen = mList.length;
        rand_pos = createRandomOrder();
        int[][] realRandomIntList = all_random_choices(rand_pos);
        String[][] stringValues = convertor(realRandomIntList);
        create_Questions(rand_pos,stringValues);
        update();
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
    //Step 1) Objective to produce an array of random numbers range[0 mList.length]
    //the array should be equal size to mList
    public int[] createRandomOrder(){
        int[ ] randomOrder = new int[mListLen];
        for(int i = 0; i< mListLen;++i){
            randomOrder[i] = i;
        }
        Shuffle s1 = new Shuffle(randomOrder);
        randomOrder = s1.shuffle();
        return randomOrder;
    }

    //Step 2) Objective to produce 3 different wrong answers
    //for each of the rows therefore 2D array. With row as a set of choices!
    public int[][] all_random_choices(int[] rand_order){
        int[][] filler = new int[mListLen][3];
        for(int i = 0; i < mListLen;++i){
            filler[i] = random_listor(rand_order[i]);
        }
        return filler;
    }

    //Step 3) Step 2 uses this process to generate 3 unique answers
    //that are also not equal to the correct answer
    public int[] random_listor(int q_num){
        int[] arr = new int[3];
        int index = 0;
        int choice1 = -1;
        int choice2 = -1;
        int num;
        Random rnd = new Random();
        while (index != 3) {
            num = rnd.nextInt(mListLen);
            if (index == 1)
                choice1 = arr[0];
            if (index == 2)
                choice2 = arr[1];
            if ((num != q_num) && (num != choice1) && (num != choice2)) {
                arr[index++] = num;
            }
        }
        return arr;
    }

    //Step 4 converts the ints generated in step 3 to string
    //answers is an string-array which takes in answer from Step 2
    //int_choices[y][x] is saying
    //ex: Question 1 choice 1 would give a number
    public String[][] convertor(int[][] int_choices){
        String[][] actual_choices = new String[mListLen][3];
        for(int y = 0; y < mListLen;++y){
            for(int x = 0; x < 3; ++x){
                actual_choices[y][x] = answers[int_choices[y][x]];//maybe (error)
            }
        }
        return actual_choices;
    }

    //Step 5
    //inserts rand_pos for questions and answers and i for list
    public void create_Questions(int[] randOrder,String[][] list){
        int rand_pos;
        for(int i = 0; i < mList.length; ++i){
            rand_pos = randOrder[i];
            mList[i] = new Questions(questions[rand_pos],answers[rand_pos],list[i]);//maybe (error)
        }
    }

    public void Choice1_handler(View view){
        is_Correct(mChoice1.getText());
    }

    public void Choice2_handler(View view){
        is_Correct(mChoice2.getText());
    }

    public void Choice3_handler(View view){
        is_Correct(mChoice3.getText());
    }

    public void Choice4_handler(View view){
        is_Correct(mChoice4.getText());
    }

    //plan on making mList.length a global variable!
    public void next_handler(View view){
        mUniversalCounter = (++mUniversalCounter)%mListLen;
        update();
    }

    public void update(){
        mQuestion.setText(mList[mUniversalCounter].getQuestion());
        mChoice1.setText(mList[mUniversalCounter].getChoices(0));
        mChoice2.setText(mList[mUniversalCounter].getChoices(1));
        mChoice3.setText(mList[mUniversalCounter].getChoices(2));
        mChoice4.setText(mList[mUniversalCounter].getChoices(3));
    }

    private void is_Correct(CharSequence user_ans){
        if(user_ans  == answers[rand_pos[mUniversalCounter]]){//rand_pos gave shuffled order
            make_Toast(R.drawable.rsz_correct);//for questions at 0 will give that
        }//particular questions answer!
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
