package com.matthewddiaz.colorlistview;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

/**
 * Created by matthew on 4/21/15.
 */
public class ColorContentProvider extends ContentProvider{
    // database
    private ColorDBHelper database;

    private static final String AUTHORITY = "com.matthewddiaz.colorlistview.provider";
    private static final String BASE_PATH = "color_table";
    public static final String CONTENT_URI_PREFIX = "content://" + AUTHORITY + "/" + BASE_PATH + "/";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    @Override
    public boolean onCreate() {
        database = new ColorDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // check if the caller has requested a column which does not exists
        ColorTable.validateProjection( projection );

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables( ColorTable.TABLE_COLORS );
        //queryBuilder.appendWhere(ColorTable.COLUMN_ID + "="  + uri.getLastPathSegment());


        SQLiteDatabase db = database.getWritableDatabase();
        if(db == null){
            Log.d("hi","-------------------------- db is null");
            return null;
        }
        Cursor cursor = queryBuilder.query( db, projection, selection,selectionArgs, null, null, sortOrder);

        // notify listeners
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType( Uri uri ) {
        return null;
    }

    @Override
    public Uri insert( Uri uri, ContentValues values ) {
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = sqlDB.insert(ColorTable.TABLE_COLORS, null, values);
        getContext().getContentResolver().notifyChange( uri, null );
        return Uri.parse( CONTENT_URI_PREFIX + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted =  sqlDB.delete(ColorTable.TABLE_COLORS, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = sqlDB.update( ColorTable.TABLE_COLORS,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}
