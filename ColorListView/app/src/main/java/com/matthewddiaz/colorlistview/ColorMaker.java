package com.matthewddiaz.colorlistview;

import android.graphics.Color;

/**
 * Created by matthew on 4/14/15.
 */
public class ColorMaker {
    private float[] hsv;

    public ColorMaker(float hue, float saturation, float value){
        hsv = new float[3];
        hsv[0] = hue;
        hsv[1] = saturation;
        hsv[2] = value;
    }

    public int makeColor(){
        return android.graphics.Color.HSVToColor(hsv);
    }


}
