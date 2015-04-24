package com.matthewddiaz.colorlistview;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by matthew on 4/21/15.
 */
public class ColorTable {
    //name of the table
    public static final String TABLE_COLORS = "color_table";

    //Column name
    public static final String COLUMN_ID = "_id";//necessary for the CursorAdapter to work!
    public static final String Column_NAME = "color_name";
    public static final String Column_HUE = "hue";
    public static final String Column_SATURATION = "saturation";
    public static final String Column_VALUE = "value";
    private static HashSet<String> VALID_COLUMN_NAMES;

    private static final String DATABASE_CREATE = "create table "

                                + TABLE_COLORS + "("
                                + COLUMN_ID + " integer primary key autoincrement, "
                                + Column_NAME + " text not null, "
                                + Column_HUE + " integer not null, "
                                + Column_SATURATION + " integer not null, "
                                + Column_VALUE + " integer not null "
                                + ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);//when this class is created

        //the Create Table sql script is written producing the table and its columns
    }

    public static void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.d(ColorTable.class.getName(),
                "Upgrading database from version "
                        + oldVersion + " to " + newVersion
                        + ", which destroyed all existing data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);//sql script for drooping table
        onCreate(database);//creating the table with the new database!
    }

    static {
        String[] validNames = {
                TABLE_COLORS,
                COLUMN_ID,
                Column_NAME,
                Column_HUE,
                Column_SATURATION,
                Column_VALUE
        };

        VALID_COLUMN_NAMES = new HashSet<String>(Arrays.asList(validNames));
    }

    public static void validateProjection(String[] projection){
        if(projection != null){
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));

            // check if all columns which are requested are available
            if ( !VALID_COLUMN_NAMES.containsAll( requestedColumns ) ) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
