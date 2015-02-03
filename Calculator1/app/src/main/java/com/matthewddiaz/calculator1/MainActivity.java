package com.matthewddiaz.calculator1;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends ActionBarActivity {
    int m0 = 0;
    int m1 = 1;
    int m2 = 2;
    int m3 = 3;
    int m4 = 4;
    int m5 = 5;
    int m6 = 6;
    int m7 = 7;
    int m8 = 8;
    int m9 = 9;
    int operand_pos = 0;
    CharSequence plus_sign = "+";
    CharSequence subtract_sign = "-";
    CharSequence multiply_sign = "*";
    CharSequence divide_sign = "/";
    CharSequence equals_sign = "=";
    int  left_operand = 0;
    int right_operand = 0;
    int  answer = 0;
    TextView mScreen;
    Button mB0;
    Button mB1;
    Button mB2;
    Button mB3;
    Button mB4;
    Button mB5;
    Button mB6;
    Button mB7;
    Button mB8;
    Button mB9;
    Button mBadd;
    Button mBsub;
    Button mBmult;
    Button mBdiv;
    Button mEquals;
    Stack<String> stack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScreen = (TextView)findViewById(R.id.screenView);
        mB0 = (Button)findViewById(R.id.button_0);
        mB1 = (Button)findViewById(R.id.button_1);
        mB2 = (Button)findViewById(R.id.button_2);
        mB3 = (Button)findViewById(R.id.button_3);
        mB4 = (Button)findViewById(R.id.button_4);
        mB5 = (Button)findViewById(R.id.button_5);
        mB6 = (Button)findViewById(R.id.button_6);
        mB7 = (Button)findViewById(R.id.button_7);
        mB8 = (Button)findViewById(R.id.button_8);
        mB9 = (Button)findViewById(R.id.button_9);
        mBadd = (Button)findViewById(R.id.button_add);
        mBsub = (Button)findViewById(R.id.button_subtract);
        mBmult = (Button)findViewById(R.id.button_multiply);
        mBdiv = (Button)findViewById(R.id.button_divide);
        mEquals = (Button)findViewById(R.id.button_equals);
        stack = new Stack<String>();

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

        return super.onOptionsItemSelected(item);
    }

    public void b0_action(View view){
        mScreen.append(String.valueOf(m0));
    }

    public void b1_action(View view){
        mScreen.append(String.valueOf(m1));
    }

    public void b2_action(View view){
        mScreen.append(String.valueOf(m2));
    }

    public void b3_action(View view){
        mScreen.append(String.valueOf(m3));
    }
    public void b4_action(View view){
        mScreen.append(String.valueOf(m4));
    }

    public void b5_action(View view){
        mScreen.append(String.valueOf(m5));
    }

    public void b6_action(View view){
        mScreen.append(String.valueOf(m6));
    }

    public void b7_action(View view){
        mScreen.append(String.valueOf(m7));
    }

    public void b8_action(View view){
        mScreen.append(String.valueOf(m8));
    }
    public void b9_action(View view){
        mScreen.append(String.valueOf(m9));
    }

    public void b_add_action(View view){
        CharSequence l1 = mScreen.getText();
        CharSequence num = l1.subSequence(operand_pos,l1.length());
        String actual_num = num.toString();
        stack.push(actual_num);
        stack.push("+");
        operand_pos = l1.length()+ 1;
        mScreen.append(String.valueOf(plus_sign));
    }

    public void b_sub_action(View view){
        CharSequence l2 = mScreen.getText();
        CharSequence num = l2.subSequence(operand_pos,l2.length());
        String actual_num = num.toString();
        stack.push(actual_num);
        stack.push("-");
        operand_pos = l2.length()+ 1;
        mScreen.append(String.valueOf(subtract_sign));
    }

    public void b_mult_action(View view){
        CharSequence l3 = mScreen.getText();
        CharSequence num = l3.subSequence(operand_pos,l3.length());
        String actual_num = num.toString();
        stack.push(actual_num);
        stack.push("*");
        operand_pos = l3.length() + 1;
        mScreen.append(String.valueOf(multiply_sign));
    }

    public void b_div_action(View view){
        CharSequence l4 = mScreen.getText();
        CharSequence num = l4.subSequence(operand_pos,l4.length());
        String actual_num = num.toString();
        stack.push(actual_num);
        stack.push("/");
        operand_pos = l4.length() + 1;
        mScreen.append(String.valueOf(divide_sign));
    }

    public void b_equals_action(View view){
        CharSequence other_ans = mScreen.getText();
        CharSequence num = other_ans.subSequence(operand_pos,other_ans.length());
        String actual_num = num.toString();
        stack.push(actual_num);
        String operation;
        while(!stack.empty()){
            right_operand = Integer.parseInt(stack.pop());
            operation = stack.pop();
            left_operand = Integer.parseInt(stack.pop());
            switch (operation){
                case "+": answer = adder(left_operand,right_operand);
                    break;
                case "-": answer = subtract(left_operand,right_operand);
                    break;
                case "*": answer = multiplier(left_operand,right_operand);
                    break;
                default:
                    if(right_operand == 0){
                        make_a_toast(getResources().getString(R.string.toast_View));
                        mScreen.setText("NaN");
                        return;
                    }
                    else{
                        answer = divider(left_operand,right_operand);
                    }
                    break;
            }
            if(!stack.empty()){
                stack.push(Integer.toString(answer));
            }
        }
        mScreen.append(String.valueOf(equals_sign + " " +  answer));
    }

    public void make_a_toast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }

    public void b_clear_action(View view){
        mScreen.setText("");
        operand_pos = 0;
    }

    private int adder(int a,int b){
        return a + b;
    }
    private int subtract(int a,int b){
        return a - b;
    }
    private int multiplier(int a,int b){
        return a * b;
    }
    private int divider(int a,int b){
        return a/b;
    }


}