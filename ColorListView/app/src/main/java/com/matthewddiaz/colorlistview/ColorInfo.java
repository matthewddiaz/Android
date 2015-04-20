package com.matthewddiaz.colorlistview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by matthew on 4/20/15.
 */
public class ColorInfo extends Fragment{
    private TextView mHueInfo;
    private TextView mSatInfo;
    private TextView mValInfo;
    private float[] colorVals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_info, container, false);
        mHueInfo = (TextView)view.findViewById(R.id.hue_range);
        mSatInfo = (TextView)view.findViewById(R.id.saturation_info);
        mValInfo = (TextView)view.findViewById(R.id.value_info);
        return view;
    }

    //public void

    public void populateDate(){
        //mHueInfo.setText();
    }

}
