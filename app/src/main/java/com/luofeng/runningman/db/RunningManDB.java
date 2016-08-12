package com.luofeng.runningman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luofeng.runningman.model.RunRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗峰 on 2016/8/12.
 */
public class RunningManDB  {
    public static final String DB_NAME = "running_man";
    public static final int VERSION = 1;
    private static RunningManDB runningManDB;
    private SQLiteDatabase db;

    private RunningManDB(Context context) {
        RunningManOpenHelper dbHelper = new RunningManOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized  static RunningManDB getInstance(Context context) {
        if ( runningManDB == null) {
            runningManDB = new RunningManDB(context);
        }
        return runningManDB;
    }

    public void saveRunRecord(RunRecord runRecord) {
        if (runRecord != null) {
            ContentValues values = new ContentValues();
            values.put("mode", runRecord.getMode());
            values.put("date_time", runRecord.getDateTime());
            values.put("distance", runRecord.getDistance());
            values.put("duration", runRecord.getDuration());
            db.insert("RunRecords", null, values);
        }
    }

    public List<RunRecord> loadRunRecord() {
        List<RunRecord> list = new ArrayList<>();
        Cursor cursor = db.query("RunRecords", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                RunRecord runRecord = new RunRecord();
                runRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
                runRecord.setMode(cursor.getString(cursor.getColumnIndex("mode")));
                runRecord.setDateTime(cursor.getString(cursor.getColumnIndex("date_time")));
                runRecord.setDistance(cursor.getString(cursor.getColumnIndex("distance")));
                runRecord.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
                list.add(runRecord);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
