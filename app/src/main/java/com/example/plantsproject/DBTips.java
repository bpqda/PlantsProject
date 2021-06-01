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
                System.out.println(mCursor.getString(NUM_COLUMN_NAME));
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

    long update(PlantTip plantTip) {
        ContentValues cv = new ContentValues();
        cv.put(TIPS_COLUMN_NAME, plantTip.getName());
        cv.put(TIPS_COLUMN_NOTES, plantTip.getNotes());
        cv.put(TIPS_COLUMN_WATERING, plantTip.getWatering());
        cv.put(TIPS_COLUMN_FEEDING, plantTip.getFeeding());
        cv.put(TIPS_COLUMN_SPRAYING, plantTip.getSpraying());

        return mDataBase.update(TABLE_NAME_TIPS, cv, TIPS_COLUMN_ID + " = ?",new String[] { String.valueOf(plantTip.getId())});
    }
    void deleteAll() {
        mDataBase.delete(TABLE_NAME_TIPS, null, null);
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
                    TIPS_COLUMN_SPRAYING+" INT); ";
            db.execSQL(queryTipsDB);

            //ContentValues cv2=new ContentValues();
            //cv2.put(TIPS_COLUMN_NAME, "Орхидея");
            //cv2.put(TIPS_COLUMN_NOTES, "Опрыскивать исключительно корни;\nПересаживать раз в 2-3 года.");
            //cv2.put(TIPS_COLUMN_WATERING, 5);
            //cv2.put(TIPS_COLUMN_FEEDING, 30);
            //cv2.put(TIPS_COLUMN_SPRAYING, 1);
            //db.insert(TABLE_NAME_TIPS, null, cv2);
            //ContentValues cv4=new ContentValues();
            //cv4.put(TIPS_COLUMN_NAME, "Фиалка");
            //cv4.put(TIPS_COLUMN_NOTES, "Нельзя опрыскивать;\n" +
            //        "Ставить в места с рассеяным солнечным светом;\n" +
            //        "Фиалки не любят резкие перепады температур.");
            //cv4.put(TIPS_COLUMN_WATERING, 5);
            //cv4.put(TIPS_COLUMN_FEEDING, 15);
            //cv4.put(TIPS_COLUMN_SPRAYING, 0);
            //db.insert(TABLE_NAME_TIPS, null, cv4);
            //ContentValues cv5=new ContentValues();
            //cv5.put(TIPS_COLUMN_NAME, "Фикус");
            //cv5.put(TIPS_COLUMN_NOTES, "Чем выше уровень влажности, тем лучше;\n" +
            //        "Не ставить под прямые солнечные лучи;\n" +
            //        "Пересаживать раз в 2-3 года.");
            //cv5.put(TIPS_COLUMN_WATERING, 7);
            //cv5.put(TIPS_COLUMN_FEEDING, 15);
            //cv5.put(TIPS_COLUMN_SPRAYING, 1);
            //db.insert(TABLE_NAME_TIPS, null, cv5);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TIPS);
            onCreate(db);
        }

    }
}







