package com.matthewddiaz.emotions;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener
{
    Spinner mEmotionList;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmotionList = (Spinner)findViewById(R.id.emotion_selector);
        mEmotionList.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(this,R.array.emotions_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmotionList.setAdapter(adapter);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pos = mEmotionList.getSelectedItemPosition();
        switch(pos) {
            case 0:
                break;
            case 1:
                simpleToast(R.drawable.rsz_affection);
                break;
            case 2:
                simpleToast(R.drawable.rsz_afraid);
                break;
            case 3:
                simpleToast(R.drawable.rsz_angry);
                break;
            case 4:
                simpleToast(R.drawable.happy1);
                break;
            case 5:
                simpleToast(R.drawable.rsz_sad);
                break;
            default:
                simpleToast(R.drawable.rsz_surprised);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void simpleToast(int imageNum){
        Toast toast = new Toast(this);
        ImageView view = new ImageView(this);
        view.setImageResource(imageNum);
        toast.setView(view);
        toast.show();
    }
}
