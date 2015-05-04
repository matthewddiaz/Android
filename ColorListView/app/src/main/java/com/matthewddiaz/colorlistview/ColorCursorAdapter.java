package com.matthewddiaz.colorlistview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by matthew on 4/21/15.
 */
public class ColorCursorAdapter extends CursorAdapter{
    // Fields/Columns from the database (projection)
    // Must include the _id column for the adapter to work

    static private final int ID = 0;
    static private final int NAME = 1;
    static private final int HUE = 2;
    static private final int SATURATION = 3;
    static private final int VALUE = 4;
    static public final String ORDER_BY= ColorTable.Column_NAME;

    static private class ViewHolder {
        TextView color_name;
        TextView color_swatch;
    }

    static public final String[] PROJECTION
            = new String[] {
            ColorTable.COLUMN_ID,
            ColorTable.Column_NAME,
            ColorTable.Column_HUE,
            ColorTable.Column_SATURATION,
            ColorTable.Column_VALUE,
    };

    public ColorCursorAdapter(Context context, Cursor cursor,int flags){
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.color_swatches, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.color_name = (TextView)row.findViewById(R.id.colorName);//The TextView color_name is equal to the TextView in color_swatches called colorName
        viewHolder.color_swatch = (TextView)row.findViewById(R.id.colorHint);
        row.setTag(viewHolder);

        return row;
    }

    private ViewHolder updateViewHolderValues(View view, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.color_name.setText(cursor.getString(NAME));
        Float h = cursor.getFloat(HUE);
        Float s = cursor.getFloat(SATURATION);
        Float v = cursor.getFloat(VALUE);
        ColorMaker color = new ColorMaker(h,s,v);
        viewHolder.color_swatch.setBackgroundColor(color.makeColor());
        return viewHolder;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = updateViewHolderValues(view,cursor);
    }

}
