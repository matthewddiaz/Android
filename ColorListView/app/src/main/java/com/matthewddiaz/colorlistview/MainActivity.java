package com.matthewddiaz.colorlistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateList();
    }

    public void populateList(){
        String[] colorList = {"blue","red","green","purple","white"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.color,colorList);
        ListView list = (ListView)findViewById(R.id.List);
        list.setAdapter(adapter);
    }
}
