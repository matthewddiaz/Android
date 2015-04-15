package com.matthewddiaz.colorlistview;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView mList;
    private List<GradientDrawable> mDrawableList;
    private ColorAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (ListView)findViewById(R.id.List);
        populateList();
    }

    public void makingGradients(float maxS,float leastS,int lVLength){
        if(mDrawableList == null){
            mDrawableList = new ArrayList<GradientDrawable>();
        }

        ColorMaker c1;
        ColorMaker c2;

        float interval =  maxS - leastS;
        float range = interval/lVLength;
        float cSat = leastS;

        GradientDrawable drawable;
        int leftColor;
        int rightColor;
        for(int i = 0; i != lVLength;++i){
           float s1 = cSat;
           cSat += range;
           c1 = new ColorMaker(s1,1.0f,1.0f);
           c2 = new ColorMaker(cSat,1.0f,1.0f);

           leftColor = c1.makeColor();
           rightColor = c2.makeColor();

           drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
           mDrawableList.add(drawable);
        }
    }

    public void populateList(){
        makingGradients(345.0f,15.0f,11);
        if(mAdapter == null){
           mAdapter = new ColorAdapter(this,mDrawableList);
        }
        mList.setAdapter(mAdapter);
    }

    /*
    public void registerClickCallback(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view;
                view.setBackground(drawable);
                String message = "The clicked #" + position + " and the color is "
                                 + textView.getText().toString();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }*/
}
