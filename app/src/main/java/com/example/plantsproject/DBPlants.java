package com.example.plantsproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBPlants {
    private static final String DATABASE_NAME = "plants.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tablePlants";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_WATERING = "Watering";
    private static final String COLUMN_FEEDING = "Feeding";
    private static final String COLUMN_SPRAYING = "Spraying";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME= 1;
    private static final int NUM_COLUMN_WATERING = 2;
    private static final int NUM_COLUMN_FEEDING = 3;
    private static final int NUM_COLUMN_SPRAYING = 4;
    private SQLiteDatabase mDataBase;


    public DBPlants(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, int watering, int feeding, int spraying) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_WATERING, watering);
        cv.put(COLUMN_FEEDING, feeding);
        cv.put(COLUMN_SPRAYING,spraying);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Plant plant) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, plant.getName());
        cv.put(COLUMN_WATERING, plant.getWatering());
        cv.put(COLUMN_FEEDING, plant.getFeeding());
        cv.put(COLUMN_SPRAYING,plant.getSpraying());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(plant.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Plant select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String plantName = mCursor.getString(NUM_COLUMN_NAME);
        int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
        int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
        int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);
        return new Plant(id, plantName, plantWatering, plantFeeding,plantSpraying);
    }

    public ArrayList<Plant> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, "id desc");

        ArrayList<Plant> arr = new ArrayList<>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String plantName = mCursor.getString(NUM_COLUMN_NAME);
                int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
                int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
                int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);
                arr.add(new Plant(id, plantName, plantWatering, plantFeeding,plantSpraying));
            } while (mCursor.moveToNext());
        }
        return arr;
    }



    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " TEXT, " +
                    COLUMN_WATERING + " INT," +
                    COLUMN_FEEDING + " INT,"+
                    COLUMN_SPRAYING+" INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}


