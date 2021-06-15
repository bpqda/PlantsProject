package com.example.plantsproject.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBPhotos {
    private static final String DATABASE_NAME = "photos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tablePhotos";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHOTOS = "photo";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_PHOTO = 1;
    private SQLiteDatabase mDataBase;


    DBPhotos(Context context) {
        DBPhotos.PhotosOpenHelper myOpenHelper = new DBPhotos.PhotosOpenHelper(context);
        mDataBase = myOpenHelper.getWritableDatabase();
    }


    long insert(long id, String path) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_PHOTOS, path);

        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    //ArrayList<String> select (long id) {
    //    Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
    //    mCursor.moveToFirst();
    //}




    private class PhotosOpenHelper extends SQLiteOpenHelper {

        PhotosOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String queryTipsDB = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER, " +
                    COLUMN_PHOTOS + " BLOB); ";
            db.execSQL(queryTipsDB);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
