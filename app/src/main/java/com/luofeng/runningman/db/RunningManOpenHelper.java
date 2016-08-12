package com.luofeng.runningman.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 罗峰 on 2016/8/12.
 */
public class RunningManOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_RUNRECORDS = "create table RunRecords ("
            + "id integer primary key autoincrement, "
            + "mode text, "
            + "date_time text, "
            + "distance text, "
            + "duration text);";

    public RunningManOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RUNRECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
