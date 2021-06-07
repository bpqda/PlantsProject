package com.example.plantsproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBTips {
    private static final String DATABASE_NAME = "tips.db";
    private static final int DATABASE_VERSION = 13;
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

    DBTips(Context context) {
        DBTips.TipsOpenHelper myOpenHelper = new DBTips.TipsOpenHelper(context);
        mDataBase = myOpenHelper.getWritableDatabase();
    }

    PlantTip findString(String str) {
        Cursor mCursor = mDataBase.query(TABLE_NAME_TIPS, null, null, null, null, null, null);
        mCursor.moveToFirst();
        if(!mCursor.isAfterLast()) {
            do {
                if (str.toLowerCase().contains(mCursor.getString(NUM_COLUMN_NAME).toLowerCase())) {

                    String plantName = mCursor.getString(NUM_COLUMN_NAME);
                    String plantNotes = mCursor.getString(NUM_COLUMN_NOTES);
                    int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
                    int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
                    int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);

                    return new PlantTip(plantName, plantWatering, plantFeeding, plantSpraying,  plantNotes);
                }
            } while (mCursor.moveToNext());
        }
        return null;
    }

    long insert(PlantTip plantTip) {
        ContentValues cv = new ContentValues();
        cv.put(TIPS_COLUMN_NAME, plantTip.getName());
        cv.put(TIPS_COLUMN_NOTES, plantTip.getNotes());
        cv.put(TIPS_COLUMN_WATERING, plantTip.getWatering());
        cv.put(TIPS_COLUMN_FEEDING, plantTip.getFeeding());
        cv.put(TIPS_COLUMN_SPRAYING, plantTip.getSpraying());

        return mDataBase.insert(TABLE_NAME_TIPS, null, cv);
    }

    //ArrayList<PlantTip> selectAll() {
    //    Cursor mCursor = mDataBase.query(TABLE_NAME_TIPS, null, null, null, null, null, "id desc");
    //    ArrayList<PlantTip> arr = new ArrayList<>();
    //    mCursor.moveToFirst();
    //    if (!mCursor.isAfterLast()) {
    //        do {
    //            String plantName = mCursor.getString(NUM_COLUMN_NAME);
    //            String plantNotes = mCursor.getString(NUM_COLUMN_NOTES);
    //            int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
    //            int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
    //            int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);
    //            arr.add(new PlantTip(plantName, plantWatering, plantFeeding, plantSpraying, plantNotes));
    //        } while (mCursor.moveToNext());
    //    }
    //    return arr;
    //}

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
                    TIPS_COLUMN_SPRAYING+" INT); ";
            db.execSQL(queryTipsDB);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TIPS);
            onCreate(db);
        }

    }
}







