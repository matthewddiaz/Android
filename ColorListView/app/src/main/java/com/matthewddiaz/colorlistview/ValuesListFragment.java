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
public class ValuesListFragment extends ListFragment {
    private List<ColorGD> mDrawableList = null;
    private ColorAdapter mAdapter = null;
    private float leftHue;
    private float rightHue;
    private float currentSat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_list, container, false);
        if(mAdapter == null){
            populateList();
        }
        return view;
    }

    private void populateList(){
        float[] satValues = getArguments().getFloatArray("saturations");
        leftHue = satValues[0];
        rightHue = satValues[1];
        currentSat = satValues[2];
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
        float cVal = maxVal;

        ColorGD drawable;
        int leftColor;
        int rightColor;
        for(int i = 0; i != lVLength;++i){
            c1 = new ColorMaker(leftHue,currentSat,cVal);
            c2 = new ColorMaker(rightHue,currentSat,cVal);

            leftColor = c1.makeColor();
            rightColor = c2.makeColor();

            drawable = new ColorGD(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{leftColor,rightColor});
            drawable.setColors(0,cVal);//saving the current Value


            mDrawableList.add(drawable);
            cVal -= range;//setting up the next color value
        }
    }

    /*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);//For clicking if using ListFragment then

        ColorGD viewItem = mDrawableList.get(position);//you can simply use its override method onListItemClick()
        float[] hues = {leftHue,rightHue,currentSat,viewItem.getColor(0)};
        //the first two are the constant Hues. The latter two are the constant saturations

        Bundle args = new Bundle();
        final String satValues = "saturations";
        args.putFloatArray(satValues,hues);

        ColorInfo cLF = new ColorInfo();
        cLF.setArguments(args);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,cLF);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

}
