package com.matthewddiaz.colorlistview;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by matthew on 4/15/15.
 */
public class ColorAdapter extends ArrayAdapter<GradientDrawable> {
    private final List<GradientDrawable> drawableList;

    public ColorAdapter(Context context,List<GradientDrawable> objects ){
        super(context,0,objects);
        drawableList = objects;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color, parent, false);
        }else{
           convertView.getTag();
        }
        GradientDrawable gd = getItem(position);
        TextView textView = (TextView)convertView.findViewById(R.id.colortext);
        textView.setBackground(gd);
        return convertView;
    }


}
