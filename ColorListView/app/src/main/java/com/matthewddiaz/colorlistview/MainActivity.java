package com.matthewddiaz.colorlistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.List);
        populateList();
        registerClickCallback();
    }

    public void populateList(){
        String[] colorList = {"blue","red","green","purple","white"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.color,colorList);
        list.setAdapter(adapter);
    }

    public void registerClickCallback(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view;
                String message = "The clicked #" + position + " and the color is "
                                 + textView.getText().toString();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
