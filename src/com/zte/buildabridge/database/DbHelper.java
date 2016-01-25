package com.zte.buildabridge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {

    final private static String DB_BAME = "ranklistdb";
    final private static int DB_VERSION = 1;
    final public static String TABLE_NAME = "ranklist";
    final public static String COL_NAME = "name";
    final public static String COL_SCORE = "score";
    final public static String COL_DATE = "date";
    final private static String INDEX_NAME = "score_index";
    final private static String STRING_CREATE = "CREATE TABLE " + TABLE_NAME
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT, "
            + COL_SCORE + " INTEGER, " + COL_DATE + " DATE);";
    final private static String STRING_INDEX = "CREATE INDEX " + INDEX_NAME
            + " ON " + TABLE_NAME + "(" + COL_SCORE + ");";

    public DbHelper(Context context) {
        super(context, DB_BAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);
        db.execSQL(STRING_INDEX);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
        // TODO
    }

}
