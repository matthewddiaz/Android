package com.matthewddiaz.montecarlopi;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    EditText mUserInput;
    TextView mPiCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserInput = (EditText)findViewById(R.id.user_input);
        mPiCal = (TextView)findViewById(R.id.answer_text);
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

    int count = 0;
    int inside = 0;
    double ans;
    public void enterHandler(View view){

        String repeats = mUserInput.getText().toString();
        final long numOfCycles = Integer.valueOf(repeats);
        new Thread(new Runnable(){
            public void run() {
                int cycles = 1000;
                while (count < cycles * numOfCycles) {
                    Pair pi_cal = new Pair();
                    if (pi_cal.is_inside()) {
                        inside++;
                    }
                    if (++count % cycles == 0) {
                        int total = cycles * (count / cycles);
                        ans = getAnswer(inside, total);
                        Runnable displayTask = new Runnable() {
                            String display = "Pi is " + Double.toString(ans);

                            @Override
                            public void run() {
                                mPiCal.setText(display);
                            }
                        };
                        mPiCal.post(displayTask);
                    }
                }
            }
        }).start();
        count = 0;//at the end of this computation count and inside are set to zero
        inside = 0;//again! If not count will be the previous value and not enter the while loop!
    }

    double getAnswer(int inside, int total){
        double pi = (4.0*inside)/total;
        return pi;
    }


}
