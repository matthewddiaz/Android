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
    long  left_operand = 0;
    long right_operand = 0;
    long answer = 0;
    int operation_choice = 0;
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
    ArrayList<Integer> operator_list = new ArrayList<Integer>();


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
        operation_choice = 1;
        mScreen.append(String.valueOf(plus_sign));
        CharSequence l1 = mScreen.getText();
        String findpos = l1.toString();
        operand_pos = findpos.indexOf("+");
    }

    public void b_sub_action(View view){
        operation_choice = 2;
        mScreen.append(String.valueOf(subtract_sign));
        CharSequence l1 = mScreen.getText();
        String findpos = l1.toString();
        operand_pos = findpos.indexOf("-");
    }

    public void b_mult_action(View view){
        operation_choice = 3;
        mScreen.append(String.valueOf(multiply_sign));
        CharSequence l1 = mScreen.getText();
        String findpos = l1.toString();
        operand_pos = findpos.indexOf("*");
    }

    public void b_div_action(View view){
        operation_choice = 4;
        mScreen.append(String.valueOf(divide_sign));
        CharSequence l1 = mScreen.getText();
        String findpos = l1.toString();
        operand_pos = findpos.indexOf("/");
    }

    public void b_equals_action(View view){
        CharSequence ans = mScreen.getText();
        String par = ans.toString();
        left_operand = Integer.parseInt(par.substring(0,operand_pos));
        right_operand = Integer.parseInt(par.substring(operand_pos+1));
        switch (operation_choice){
            case 1: answer = adder(left_operand,right_operand);
                    break;
            case 2: answer = subtract(left_operand,right_operand);
                    break;
            case 3: answer = multiplier(left_operand,right_operand);
                    break;
            default:
                    if(right_operand == 0){
                        make_a_toast(getResources().getString(R.string.toast_View));
                        mScreen.setText("NaN");
                    }
                    else{
                        answer = divider(left_operand,right_operand);
                    }
                    break;
        }
        if(mScreen.getText().toString().equals("NaN")){
          return;
        }
        mScreen.append(String.valueOf(equals_sign + " " +  answer));
        left_operand = answer;
    }

    public void make_a_toast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }

    public void b_clear_action(View view){
        mScreen.setText("");
    }

    private long adder(long a,long b){
        return a + b;
    }
    private long subtract(long a,long b){
        return a - b;
    }
    private long multiplier(long a,long b){
        return a * b;
    }
    private long divider(long a,long b){
        return a/b;
    }


}
