package com.matthewddiaz.colorlistview;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    public static final String MyPREF = "MyPrefs";
    public static  final String IS_OPEN = "false";
    private SQLiteDatabase database = null;
    private ColorDBHelper  dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new ColorDBHelper(this);
        database = dbHelper.getWritableDatabase();
        sharedPreferences = getSharedPreferences(MyPREF, Context.MODE_PRIVATE);
        addListFragment();
        Boolean bool = sharedPreferences.getBoolean(IS_OPEN, true);
        if(bool){
            Log.d(null,"Do you enter");
            new DataTask().execute();
        }
    }


    public void addListFragment(){
        ColorListFragment cLF = new ColorListFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,cLF);
        transaction.commit();
    }

    public class DataTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(null,"inserting database");
            ContentValues contentValues = new ContentValues();
            String[] names = getResources().getStringArray(R.array.names);
            String[] hues = getResources().getStringArray(R.array.hues);
            String[]saturations = getResources().getStringArray(R.array.saturation);
            String[] values = getResources().getStringArray(R.array.values);
            Log.d(null,"The length is " + names.length);
            for(int i = 0; i < names.length; i++){

                contentValues.put(ColorTable.Column_NAME,names[i]);
                contentValues.put(ColorTable.Column_HUE, hues[i]);
                contentValues.put(ColorTable.Column_SATURATION,saturations[i]);
                contentValues.put(ColorTable.Column_VALUE, values[i]);
                database.insert(ColorTable.TABLE_COLORS, null, contentValues);
            }

            sharedPreferences = getSharedPreferences(MyPREF, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(IS_OPEN, false);
            editor.apply();
            return null;
        }
    }
}
