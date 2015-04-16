package com.matthewddiaz.colorlistview;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by matthew on 4/16/15.
 */

/*when a ColorGD is created in makingGradients() the left and right hues of
that Gradient needs to be saved. This is done with the setColors() method. This
method does not exist in GradientDrawable which is why I extended the class.
*/
public class ColorGD extends GradientDrawable {
    private float[] hues;

    public ColorGD(GradientDrawable.Orientation orientation, int[] colors){
        super(orientation,colors);
        hues = new float[2];
    }

    public void setColors(int slot,float hue){
        hues[slot]= hue;
    }

    public float getColor(int slot){
        return hues[slot];
    }
}
