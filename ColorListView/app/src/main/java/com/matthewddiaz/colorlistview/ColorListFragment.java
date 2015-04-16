package com.matthewddiaz.colorlistview;


import android.app.ListFragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


public class ColorListFragment extends ListFragment implements OnItemClickListener{
    private List<ColorGD> mDrawableList;
    private List<ColorGD> mDrawableList2;

    private ColorAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        populateList();
        getListView().setOnItemClickListener(this);
    }

    public void makingGradients(float minH,int lVLength){
        float maxVal = 360.0f;//Hue ranges from [0f - 360f]
        if(mDrawableList == null){
            mDrawableList = new ArrayList<ColorGD>();
        }

        ColorMaker c1;
        ColorMaker c2;

        float range = maxVal/lVLength;
        float cHue = minH;

        ColorGD drawable;
        int leftColor;
        int rightColor;
        for(int i = 0; i != lVLength;++i){
            float leftHue = cHue%maxVal;//s1 is the value of the left side if min passes 360 then modulo!
            cHue = (leftHue + range)%maxVal;//the right side is the left side + range. Just in case modulo 360!

            c1 = new ColorMaker(leftHue,1.0f,1.0f);
            c2 = new ColorMaker(cHue,1.0f,1.0f);

            leftColor = c1.makeColor();
            rightColor = c2.makeColor();

            drawable = new ColorGD(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
            drawable.setColors(0,leftHue);
            drawable.setColors(1,cHue);
            mDrawableList.add(drawable);
        }
    }

    public void makingSaturation(float leftHue,float rightHue,int lVLength){
        float maxVal = 1.0f;//Saturation ranges from [0f - 1.0f]
        mDrawableList2  = new ArrayList<ColorGD>();

        ColorMaker c1;
        ColorMaker c2;

        float range = maxVal/lVLength;
        float cSat = maxVal;

        ColorGD drawable;
        int leftColor;
        int rightColor;
        for(int i = 0; i != lVLength;++i){
            float leftSat = cSat;
            cSat = leftSat - range;//the right side is the left side + range. Just in case modulo 360!

            c1 = new ColorMaker(leftHue,leftSat,1.0f);
            c2 = new ColorMaker(rightHue,cSat,1.0f);

            leftColor = c1.makeColor();
            rightColor = c2.makeColor();

            drawable = new ColorGD(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
            mDrawableList2.add(drawable);
        }
    }



    public void populateList(){
        makingGradients(345.0f,12);
        if(mAdapter == null){
            mAdapter = new ColorAdapter(this.getActivity() ,mDrawableList);
        }
        setListAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        ColorGD item = mDrawableList.get(position);
        float leftHue = item.getColor(0);
        float rightHue = item.getColor(1);
        makingSaturation(leftHue, rightHue, 10);

        mAdapter = new ColorAdapter(this.getActivity() ,mDrawableList2);
        setListAdapter(mAdapter);
    }

}
