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
        registerClickCallback();
    }

    public void makingGradients(float minS,int lVLength){
        float maxVal = 360.0f;//Saturation ranges from [0f - 360f]
        if(mDrawableList == null){
            mDrawableList = new ArrayList<GradientDrawable>();
        }

        ColorMaker c1;
        ColorMaker c2;

        float range = maxVal/lVLength;
        float cSat = minS;

        GradientDrawable drawable;
        int leftColor;
        int rightColor;
        for(int i = 0; i != lVLength;++i){
           float leftSide = cSat%maxVal;//s1 is the value of the left side if min passes 360 then modulo!
           cSat = (leftSide + range)%maxVal;//the right side is the left side + range. Just in case modulo 360!

           c1 = new ColorMaker(leftSide,1.0f,1.0f);
           c2 = new ColorMaker(cSat,1.0f,1.0f);

           leftColor = c1.makeColor();
           rightColor = c2.makeColor();

           drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
           mDrawableList.add(drawable);
        }
    }

    public void populateList(){
        makingGradients(345.0f,12);
        if(mAdapter == null){
           mAdapter = new ColorAdapter(this,mDrawableList);
        }
        mList.setAdapter(mAdapter);
    }


    public void registerClickCallback(){
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "You clicked position" + position;
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
