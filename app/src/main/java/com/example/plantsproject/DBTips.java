package com.example.plantsproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTips {
    Context ctx;
    private static final String DATABASE_NAME = "tips.db";
    private static final int DATABASE_VERSION = 8;
    private static final String TABLE_NAME_TIPS = "tableTips";

    private static final String TIPS_COLUMN_ID = "id";
    private static final String TIPS_COLUMN_NAME= "Name";
    private static final String TIPS_COLUMN_NOTES = "Notes";
    private static final String TIPS_COLUMN_WATERING = "Watering";
    private static final String TIPS_COLUMN_FEEDING = "Feeding";
    private static final String TIPS_COLUMN_SPRAYING = "Spraying";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_NOTES = 2;
    private static final int NUM_COLUMN_WATERING = 3;
    private static final int NUM_COLUMN_FEEDING = 4;
    private static final int NUM_COLUMN_SPRAYING = 5;
    private SQLiteDatabase mDataBase;

    public DBTips(Context context) {
        DBTips.TipsOpenHelper myOpenHelper = new DBTips.TipsOpenHelper(context);
        mDataBase = myOpenHelper.getWritableDatabase();
    }

    public Plant findString(String str) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_TIPS, null, null, null, null, null, null);
        mCursor.moveToFirst();
        if(!mCursor.isAfterLast()) {
            do {
                System.out.println(mCursor.getString(NUM_COLUMN_NAME));
                if (mCursor.getString(NUM_COLUMN_NAME).equals(str)) {
                    long id = mCursor.getLong(NUM_COLUMN_ID);
                    String plantName = mCursor.getString(NUM_COLUMN_NAME);
                    String plantNotes = mCursor.getString(NUM_COLUMN_NOTES);
                    int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
                    int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
                    int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);
                    return new Plant(id, plantName, plantNotes, plantWatering, plantFeeding, plantSpraying);
                }
            } while (mCursor.moveToNext());
        }
        return null;
    }

    public long insert(String name, String notes, int watering, int feeding, int spraying) {
        ContentValues cv=new ContentValues();
        cv.put(TIPS_COLUMN_NAME, name);
        cv.put(TIPS_COLUMN_NOTES, notes);
        cv.put(TIPS_COLUMN_WATERING, watering);
        cv.put(TIPS_COLUMN_FEEDING, feeding);
        cv.put(TIPS_COLUMN_SPRAYING,spraying);
        return mDataBase.insert(TABLE_NAME_TIPS, null, cv);
    }

    private class TipsOpenHelper extends SQLiteOpenHelper {

        TipsOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String queryTipsDB = "CREATE TABLE " + TABLE_NAME_TIPS + " (" +
                    TIPS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIPS_COLUMN_NAME+ " TEXT, " +
                    TIPS_COLUMN_NOTES+" TEXT, "+
                    TIPS_COLUMN_WATERING + " INT," +
                    TIPS_COLUMN_FEEDING + " INT,"+
                    TIPS_COLUMN_SPRAYING+" INT);";
            db.execSQL(queryTipsDB);

            ContentValues cv=new ContentValues();
            cv.put(TIPS_COLUMN_NAME, "Огурец");
            cv.put(TIPS_COLUMN_NOTES, "Заметки");
            cv.put(TIPS_COLUMN_WATERING, 3);
            cv.put(TIPS_COLUMN_FEEDING, 3);
            cv.put(TIPS_COLUMN_SPRAYING,3);

            db.insert(TABLE_NAME_TIPS, null, cv);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TIPS);
            onCreate(db);
        }

    }
}
