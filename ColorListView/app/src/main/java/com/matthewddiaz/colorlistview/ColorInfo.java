package com.matthewddiaz.colorlistview;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by matthew on 4/20/15.
 */
public class ColorInfo  extends ListFragment {
    public static final String MyPREF = "MyPrefs";
    private float[] colorVals;
    private Cursor mCursor;
    private Button mButton;
    private Uri uri;
    private String[] projection;
    private String[] args;
    private String selection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_with_alert, container, false);
        float stanDev = 20F;
        colorVals = getArguments().getFloatArray("colorVals");
        float hueLeft =  colorVals[0];
        float hueRight = colorVals[1];

        uri = ColorContentProvider.CONTENT_URI;
        projection = ColorCursorAdapter.PROJECTION;

        if ( hueLeft > hueRight ) {
            selection = "(" + ColorTable.Column_HUE + " >=? or " + ColorTable.Column_HUE+ " <=? ) and " +
                    ColorTable.Column_SATURATION + " >=? and " + ColorTable.Column_SATURATION + " <=? and " +
                    ColorTable.Column_VALUE + " >=? and " + ColorTable.Column_VALUE + " <=?";
        } else {
            selection = ColorTable.Column_HUE + " >=? and " + ColorTable.Column_HUE + " <=? and " +
                    ColorTable.Column_SATURATION + " >=? and " + ColorTable.Column_SATURATION + " <=? and " +
                    ColorTable.Column_VALUE + " >=? and " + ColorTable.Column_VALUE + " <=?";
        }

        String[] selectionArgs = {Float.toString( hueLeft),
                                  Float.toString(hueRight),
                                  Float.toString(colorVals[2]*100 - stanDev),
                                  Float.toString(colorVals[2]*100 + stanDev),
                                  Float.toString(colorVals[3]*100 - stanDev),
                                  Float.toString(colorVals[3]*100 + stanDev)};

        args = new String[selectionArgs.length];
        for(int i = 0; i != args.length;++i){//to not have to recalculate sectionArgs again passing it to this array args
            args[i] = selectionArgs[i];
        }

        SharedPreferences preferences = getActivity().getSharedPreferences(MyPREF,Context.MODE_PRIVATE);
        String sort = preferences.getString("key",null);
        mCursor = ((MainActivity)getActivity()).getContentResolver().query(
                uri,
                projection,
                selection,
                selectionArgs,
                sort);
        if(mCursor != null)
            mCursor.moveToFirst();

        ColorCursorAdapter cCA = new ColorCursorAdapter(getActivity(),mCursor,0);
        setListAdapter(cCA);

        mButton = (Button)view.findViewById(R.id.sort_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sort();
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCursor.moveToPosition(position);
        float hue = mCursor.getFloat(mCursor.getColumnIndex(ColorTable.Column_HUE));
        Log.d(null,"Value of Column_Hue " + mCursor.getColumnIndex(ColorTable.Column_HUE));
        float sat = mCursor.getFloat(mCursor.getColumnIndex(ColorTable.Column_SATURATION));
        float value = mCursor.getFloat(mCursor.getColumnIndex(ColorTable.Column_VALUE));
        CharSequence vals = getColorInfo(hue,sat,value);
        makeToast(vals);
    }

    public String getColorInfo(float hue,float sat,float val){//rounding the float to the nearest int then turning to a String value
        StringBuffer sb = new StringBuffer();
        String h = "Hue is at " + Integer.toString(Math.round(hue)) + "°" + " \n";
        String s = "Saturation is at " + Integer.toString(Math.round(sat)) + "%" +" \n";
        String v = "Value is at " + Integer.toString(Math.round(val)) + "%" + " \n";
        sb.append(h);
        sb.append(s);
        sb.append(v);
        return sb.toString();
    }

    public void makeToast(CharSequence text){
        Context context = getActivity();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }

    public void sort(){
       CharSequence[] options = {"Hue, Saturation, Value","Hue, Value, Saturation",
                                "Saturation, Hue, Value","Saturation, Value, Hue",
                                "Value, Hue, Saturation","Value, Saturation, Hue"};
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Sort By").setSingleChoiceItems(options, -1,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            String sortBy;
                            switch (position) {
                                    case 0:
                                         sortBy = ColorTable.Column_HUE + "," +  ColorTable.Column_SATURATION + "," + ColorTable.Column_VALUE;
                                        break;
                                    case 1:
                                        sortBy = ColorTable.Column_HUE + "," + ColorTable.Column_VALUE + ","  + ColorTable.Column_SATURATION;
                                        break;
                                    case 2:
                                        sortBy = ColorTable.Column_SATURATION + "," + ColorTable.Column_HUE + "," + ColorTable.Column_VALUE;
                                        break;
                                    case 3:
                                        sortBy = ColorTable.Column_SATURATION + "," + ColorTable.Column_VALUE + "," + ColorTable.Column_HUE;
                                        break;
                                    case 4:
                                        sortBy = ColorTable.Column_VALUE + "," + ColorTable.Column_HUE + "," + ColorTable.Column_SATURATION;
                                        break;
                                    case 5:
                                        sortBy = ColorTable.Column_VALUE + "," + ColorTable.Column_SATURATION + "," + ColorTable.Column_VALUE;
                                        break;
                                    default:
                                        sortBy = ColorTable.Column_HUE + "," + ColorTable.Column_SATURATION + "," + ColorTable.Column_VALUE;
                                        break;
                                }
                            SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREF, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("key", sortBy);
                            editor.commit();

                            mCursor = ((MainActivity)getActivity()).getContentResolver().query(
                                    uri,
                                    projection,
                                    selection,
                                    args,
                                    sortBy);
                            if(mCursor != null)
                                mCursor.moveToFirst();

                            ColorCursorAdapter cCA = new ColorCursorAdapter(getActivity(),mCursor,0);
                            setListAdapter(cCA);
                            dialog.dismiss();
                            }
                        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
