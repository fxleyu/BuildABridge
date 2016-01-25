package com.zte.buildabridge.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zte.buildabridge.object.Record;

public class DbUtils {

    private SQLiteDatabase db;
    final private static String SELECTION = DbHelper.COL_SCORE + " >= ?";
    final private static String ORDER_BY = DbHelper.COL_SCORE + " DESC";
    final private static String TOP10 = "10";
    private static final String TOP1 = "1";
    @SuppressLint("SimpleDateFormat")
    final private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    

    public DbUtils(Context context) {
        db = new DbHelper(context).getWritableDatabase();
    }

    public void insertRecord(Record record) {
        System.out.println(record.toString());
        ContentValues cv = new ContentValues(3);
        cv.put(DbHelper.COL_NAME, record.getName());
        cv.put(DbHelper.COL_SCORE, record.getScore());
        cv.put(DbHelper.COL_DATE, dateFormat.format(new Date()));
        long l = db.insert(DbHelper.TABLE_NAME, null, cv);
        System.out.println(l + "   database  !!");
    }

    public boolean isTop1(int score) {
        Cursor cusor = db.query(DbHelper.TABLE_NAME, null, SELECTION,
                new String[] { score + "" }, null, null, null);
        return cusor.getCount() > 0 ? false : true;
    }

    public List<Record> top10Record() {
        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, null, null, null,
                null, ORDER_BY, TOP10);
        List<Record> result = new ArrayList<Record>(Integer.valueOf(TOP10));
        while (cursor.moveToNext()) {
            Record r = new Record(cursor.getString(1), cursor.getInt(2),
                    cursor.getString(3));
            System.out.println(r);
            result.add(r);
        }
        return result;
    }

    public Record top1Record() {
        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, null, null, null,
                null, ORDER_BY, TOP1);
        cursor.moveToNext();
        Record result = new Record(cursor.getString(1), cursor.getInt(2),
                cursor.getString(3));
        return result;
    }

    public void close() {
        db.close();
    }
}
