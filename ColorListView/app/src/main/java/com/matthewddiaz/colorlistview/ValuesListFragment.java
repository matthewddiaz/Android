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
public class ValuesListFragment extends ListFragment {
    private List<ColorGD> mDrawableList = null;
    private ColorAdapter mAdapter = null;
    private float leftHue;
    private float rightHue;
    private float leftSat;
    private float rightSat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_list, container, false);
        super.onActivityCreated(savedInstanceState);
        float[] satValues = getArguments().getFloatArray("saturations");
        leftHue = satValues[0];
        rightHue = satValues[1];
        leftSat = satValues[2];
        rightSat = satValues[3];
        makingSaturation(10);

        if(mAdapter == null){
            mAdapter = new ColorAdapter(this.getActivity() ,mDrawableList);
        }
        setListAdapter(mAdapter);

        return view;
    }

    public void makingSaturation(int lVLength){
        float maxVal = 1.0f;//Saturation ranges from [0f - 1.0f]
        if(mDrawableList == null){
            mDrawableList  = new ArrayList<ColorGD>();
        }

        ColorMaker c1;
        ColorMaker c2;

        float range = maxVal/lVLength;
        float cVal = maxVal;

        ColorGD drawable;
        int leftColor;
        int rightColor;
        for(int i = 0; i != lVLength;++i){
            float leftVal = cVal;
            cVal = leftVal - range;//the right side is the left side + range. Just in case modulo 360!

            c1 = new ColorMaker(leftHue,leftSat,leftVal);
            c2 = new ColorMaker(rightHue,rightSat,cVal);

            leftColor = c1.makeColor();
            rightColor = c2.makeColor();

            drawable = new ColorGD(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
            mDrawableList.add(drawable);
        }
    }

}
