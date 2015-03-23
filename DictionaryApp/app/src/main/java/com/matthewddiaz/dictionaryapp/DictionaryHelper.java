package com.matthewddiaz.dictionaryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matthew on 3/13/15.
 */

public class DictionaryHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database_dictionary";
    public static final String DICTIONARY_TABLE_NAME = "dictionary";
    public static final String KEY_TERM = "term";
    public static final String KEY_DEFINITION = "definition";

    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME +
                    " (" + KEY_TERM + " TEXT, " + KEY_DEFINITION + " TEXT);";

    public DictionaryHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( DICTIONARY_TABLE_CREATE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVer, int newVer ) {
        db.execSQL( "DROP TABLE IF EXISTS " + DICTIONARY_TABLE_NAME );
        onCreate( db );
    }
}