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

    static public final String[] PROJECTION
            = new String[] {
            ColorTable.COLUMN_ID,
            ColorTable.Column_NAME,
            ColorTable.Column_HUE,
            ColorTable.Column_SATURATION,
            ColorTable.Column_VALUE,
    };

    private LayoutInflater mInflater;

    public ColorCursorAdapter(Context context, Cursor cursor,int flags){
        super(context,cursor,flags);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View row= mInflater.inflate(R.layout.color, parent, false);
        TextView text = (TextView)row.findViewById(R.id.colortext);
        text.setText(cursor.getString(NAME));
        return row;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    }

}
