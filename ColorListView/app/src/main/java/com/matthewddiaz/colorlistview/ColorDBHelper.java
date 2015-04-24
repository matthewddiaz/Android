package com.matthewddiaz.colorlistview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by matthew on 4/21/15.
 */
public class ColorDBHelper extends SQLiteOpenHelper{//Class ColorContentProvider makes new object!
    private static String DATABASE_NAME = ColorTable.TABLE_COLORS;
    private static final int DATABASE_VERSION = 1;

    public ColorDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        ColorTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVerion,int newVersion){
        ColorTable.onUpgrade(db,oldVerion,newVersion);
    }


}
