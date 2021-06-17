package com.example.plantsproject.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.plantsproject.entitys.Plant;

import java.util.ArrayList;
import java.util.List;

public class DBPlants {
    private static final String DATABASE_NAME = "plants.db";
    private static final int DATABASE_VERSION = 16;
    private static final String TABLE_NAME = "tablePlants";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_NOTES = "Notes";
    private static final String COLUMN_WATERING = "Watering";
    private static final String COLUMN_FEEDING = "Feeding";
    private static final String COLUMN_SPRAYING = "Spraying";
    private static final String COLUMN_CREATION = "Creation";
    private static final String COLUMN_LASTWAT = "WateringLast";
    private static final String COLUMN_LASTFEED = "FeedingLast";
    private static final String COLUMN_LASTSPR = "SprayingLast";
    private static final String COLUMN_LASTMILWAT= "WateringLastInMillis";
    private static final String COLUMN_LASTMILFEED= "FeedingLastInMillis";
    private static final String COLUMN_LASTMILSPRAY= "SprayingLastInMillis";
    private static final String COLUMN_PHOTO= "photo";
    private static final String COLUMN_URL= "url";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME= 1;
    private static final int NUM_COLUMN_NOTES= 2;
    private static final int NUM_COLUMN_WATERING = 3;
    private static final int NUM_COLUMN_FEEDING = 4;
    private static final int NUM_COLUMN_SPRAYING = 5;
    private static final int NUM_COLUMN_CREATION= 6;
    private static final int NUM_COLUMN_LASTWAT= 7;
    private static final int NUM_COLUMN_LASTFEED= 8;
    private static final int NUM_COLUMN_LASTSPR= 9;
    private static final int NUM_COLUMN_LASTMILWAT= 10;
    private static final int NUM_COLUMN_LASTMILFEED= 11;
    private static final int NUM_COLUMN_LASTMILSPRAY= 12;
    private static final int NUM_COLUMN_PHOTO= 13;
    private static final int NUM_COLUMN_URL= 14;
    private SQLiteDatabase mDataBase;

    public DBPlants(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(Plant plant) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, plant.getName());
        cv.put(COLUMN_NOTES, plant.getNotes());
        cv.put(COLUMN_WATERING, plant.getWatering());
        cv.put(COLUMN_FEEDING, plant.getFeeding());
        cv.put(COLUMN_SPRAYING,plant.getSpraying());
        cv.put(COLUMN_CREATION, plant.getCreationDate());
        cv.put(COLUMN_LASTWAT, plant.getLastW());
        cv.put(COLUMN_LASTFEED, plant.getLastF());
        cv.put(COLUMN_LASTSPR, plant.getLastS());
        cv.put(COLUMN_LASTMILWAT, plant.getLastMilWat());
        cv.put(COLUMN_LASTMILFEED, plant.getLastMilFeed());
        cv.put(COLUMN_LASTMILSPRAY, plant.getLastMilSpray());
        cv.put(COLUMN_PHOTO, plant.getPhoto());
        cv.put(COLUMN_URL, plant.getUrl());
        long id =  mDataBase.insert(TABLE_NAME, null, cv);
        plant.setId(id);
        return id;
    }

    public int update(Plant plant) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, plant.getName());
        cv.put(COLUMN_NOTES, plant.getNotes());
        cv.put(COLUMN_WATERING, plant.getWatering());
        cv.put(COLUMN_FEEDING, plant.getFeeding());
        cv.put(COLUMN_SPRAYING,plant.getSpraying());
        cv.put(COLUMN_CREATION, plant.getCreationDate());
        cv.put(COLUMN_LASTWAT, plant.getLastW());
        cv.put(COLUMN_LASTFEED, plant.getLastF());
        cv.put(COLUMN_LASTSPR, plant.getLastS());
        cv.put(COLUMN_LASTMILWAT, plant.getLastMilWat());
        cv.put(COLUMN_LASTMILFEED, plant.getLastMilFeed());
        cv.put(COLUMN_LASTMILSPRAY, plant.getLastMilSpray());
        cv.put(COLUMN_PHOTO, plant.getPhoto());
        cv.put(COLUMN_URL, plant.getUrl());
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
        if(mCursor.isAfterLast()) {
            return null;
        }
        String plantName = mCursor.getString(NUM_COLUMN_NAME);
        String plantNotes = mCursor.getString(NUM_COLUMN_NOTES);
        int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
        int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
        int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);
        String creationDate = mCursor.getString(NUM_COLUMN_CREATION);
        String lastW = mCursor.getString(NUM_COLUMN_LASTWAT);
        String lastF = mCursor.getString(NUM_COLUMN_LASTFEED);
        String lastS = mCursor.getString(NUM_COLUMN_LASTSPR);
        long lastMilWat = mCursor.getLong(NUM_COLUMN_LASTMILWAT);
        long lastMilFeed = mCursor.getLong(NUM_COLUMN_LASTMILFEED);
        long lastMilSpray = mCursor.getLong(NUM_COLUMN_LASTMILSPRAY);
        int photo = mCursor.getInt(NUM_COLUMN_PHOTO);
        String url = mCursor.getString(NUM_COLUMN_URL);
        return new Plant(id, plantName, plantNotes, plantWatering,
                plantFeeding, plantSpraying, creationDate, lastW,
                lastF, lastS, lastMilWat, lastMilFeed, lastMilSpray, photo, url);
    }

    public ArrayList<Plant> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, "id desc");

        ArrayList<Plant> arr = new ArrayList<>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String plantName = mCursor.getString(NUM_COLUMN_NAME);
                String plantNotes = mCursor.getString(NUM_COLUMN_NOTES);
                int plantWatering = mCursor.getInt(NUM_COLUMN_WATERING);
                int plantFeeding = mCursor.getInt(NUM_COLUMN_FEEDING);
                int plantSpraying = mCursor.getInt(NUM_COLUMN_SPRAYING);
                String creationDate = mCursor.getString(NUM_COLUMN_CREATION);
                String lastW = mCursor.getString(NUM_COLUMN_LASTWAT);
                String lastF = mCursor.getString(NUM_COLUMN_LASTFEED);
                String lastS = mCursor.getString(NUM_COLUMN_LASTSPR);
                long lastMilWat = mCursor.getLong(NUM_COLUMN_LASTMILWAT);
                long lastMilFeed = mCursor.getLong(NUM_COLUMN_LASTMILFEED);
                long lastMilSpray = mCursor.getLong(NUM_COLUMN_LASTMILSPRAY);
                int photo = mCursor.getInt(NUM_COLUMN_PHOTO);
                String url = mCursor.getString(NUM_COLUMN_URL);
                arr.add(new Plant(id, plantName, plantNotes, plantWatering,
                        plantFeeding, plantSpraying, creationDate, lastW, lastF, lastS, lastMilWat,
                        lastMilFeed, lastMilSpray, photo, url));
            } while (mCursor.moveToNext());
        }
        return arr;

    }

    public  class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String queryPlantsDB = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " TEXT, " +
                    COLUMN_NOTES+" TEXT, "+
                    COLUMN_WATERING + " INT," +
                    COLUMN_FEEDING + " INT,"+
                    COLUMN_SPRAYING+ " INT, "+
                    COLUMN_CREATION+ " TEXT, " +
                    COLUMN_LASTWAT+ " TEXT, " +
                    COLUMN_LASTFEED+ " TEXT, " +
                    COLUMN_LASTSPR+ " TEXT, " +
                    COLUMN_LASTMILWAT + " INTEGER, " +
                    COLUMN_LASTMILFEED + " INTEGER, " +
                    COLUMN_LASTMILSPRAY + " INTEGER, " +
                    COLUMN_PHOTO + " INTEGER, " +
                    COLUMN_URL + " TEXT);";

                    db.execSQL(queryPlantsDB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}


