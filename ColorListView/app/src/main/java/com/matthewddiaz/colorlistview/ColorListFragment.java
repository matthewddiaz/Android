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


public class ColorListFragment extends ListFragment {
    private List<ColorGD> mDrawableList = null;
    private ColorAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.first_fragment, container, false);
        if(mAdapter == null){
            populateList();
        }

        return view;
    }

    private void populateList(){
        makingGradients(345.0f,12);
        if(mAdapter == null){
            mAdapter = new ColorAdapter(this.getActivity() ,mDrawableList);
        }
        setListAdapter(mAdapter);
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
            drawable.setColors(0,leftHue);//saving the left hue
            drawable.setColors(1,cHue);//saving the right hue
            mDrawableList.add(drawable);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);//For clicking if using ListFragment then
        ColorGD viewItem = mDrawableList.get(position);//you can simply use its override method onListItemClick()
        float[] hues = {viewItem.getColor(0),viewItem.getColor(1)};

        Bundle args = new Bundle();
        final String hueValues = "hues";
        args.putFloatArray(hueValues,hues);

        SaturationListFragment sLF = new SaturationListFragment();
        sLF.setArguments(args);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,sLF);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
