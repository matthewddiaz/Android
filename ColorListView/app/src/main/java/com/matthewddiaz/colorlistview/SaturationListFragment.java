package com.matthewddiaz.colorlistview;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 4/19/15.
 */
public class SaturationListFragment extends ListFragment {
    private List<ColorGD> mDrawableList = null;
    private ColorAdapter mAdapter = null;
    private float leftHue;
    private float rightHue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_list, container, false);
        if(mAdapter == null){
            populateList();
        }
        return view;
    }

    private void populateList(){
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
        for(int i = 0; i != lVLength;++i){ //from [1.0f to 0.0f]
            c1 = new ColorMaker(leftHue,cSat,1.0f);
            c2 = new ColorMaker(rightHue,cSat,1.0f);

            leftColor = c1.makeColor();
            rightColor = c2.makeColor();

            drawable = new ColorGD(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
            drawable.setColors(0,cSat);//saving the current Saturation

            mDrawableList.add(drawable);
            cSat -= range;//Creating the next Saturation level
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);//For clicking if using ListFragment then
        ColorGD viewItem = mDrawableList.get(position);//you can simply use its override method onListItemClick()
        float[] colorVals = {leftHue,rightHue,viewItem.getColor(0)};
        //the first two are the constant Hues. The latter two are the constant saturations

        Bundle args = new Bundle();
        final String satValues = "saturations";
        args.putFloatArray(satValues,colorVals);

        ValuesListFragment vLF = new ValuesListFragment();
        vLF.setArguments(args);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,vLF);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
