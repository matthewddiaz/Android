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
    private TextView mHueInfo = null;
    private TextView mSatInfo;
    private TextView mValInfo;
    private float[] colorVals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_info, container, false);
        if(mHueInfo == null){
            populateDate(view);
        }
        return view;
    }

    public void populateDate(View view){
        mHueInfo = (TextView)view.findViewById(R.id.hue_range);
        mSatInfo = (TextView)view.findViewById(R.id.saturation_info);
        mValInfo = (TextView)view.findViewById(R.id.value_info);
        colorVals = getArguments().getFloatArray("colorVals");
        setHueText();
        setSaturationText();
        setValueText();
    }

    public void setHueText(){
        mHueInfo.setText("Hues Range from " + Float.toString(colorVals[0]) + "° to " + Float.toString(colorVals[1]) + "°");
    }

    public void setSaturationText(){
        mSatInfo.setText("Saturation is at " + Float.toString(colorVals[2]*100.0F) + "%");
    }

    public void setValueText(){
        mValInfo.setText("Value is at " + Float.toString(colorVals[3]*100.0F) + "%");
    }
}
