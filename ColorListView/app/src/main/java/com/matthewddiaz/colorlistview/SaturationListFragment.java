package com.matthewddiaz.colorlistview;

import android.app.ListFragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 4/19/15.
 */
public class SaturationListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private List<ColorGD> mDrawableList = null;
    private ColorAdapter mAdapter = null;
    private float leftHue;
    private float rightHue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        float[] hues = getArguments().getFloatArray("hues");
        leftHue = hues[0];
        rightHue = hues[1];
        makingSaturation(10);

        if(mAdapter == null){
            mAdapter = new ColorAdapter(this.getActivity() ,mDrawableList);
        }
        setListAdapter(mAdapter);
    }

    public void makingSaturation(int lVLength){
        float maxVal = 1.0f;//Saturation ranges from [0f - 1.0f]
        if(mDrawableList == null){
            mDrawableList  = new ArrayList<ColorGD>();
        }

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
            mDrawableList.add(drawable);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
       /*The below is all wrong!


        ColorListFragment cLF = new ColorListFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,cLF,"Fragment1");
        transaction.commit();*/
        /*
        ColorGD item = mDrawableList.get(position);
        float leftHue = item.getColor(0);
        float rightHue = item.getColor(1);
        makingSaturation(leftHue, rightHue, 10);

        mAdapter = new ColorAdapter(this.getActivity() ,mDrawableList2);
        setListAdapter(mAdapter);*/
    }


}
