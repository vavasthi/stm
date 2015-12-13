package com.avasthi.roadcompanion.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by vavasthi on 13/12/15.
 */
public class RCDatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RoadCompanion.db";
    public static final String RC_TABLE_NAME = "SensorData";
    public static final String RC_COLUMN_ID = "_ID";
    public static final String RC_COLUMN_TIMESTAMP = "timestamp";
    public static final String RC_COLUMN_ACCURACY = "accuracy";
    public static final String RC_COLUMN_BEARING = "bearing";
    public static final String RC_COLUMN_LATITUDE = "latitude";
    public static final String RC_COLUMN_LONGITUDE = "longitude";
    public static final String RC_COLUMN_SPEED = "speed";
    public static final String RC_COLUMN_ACCELEROMETER_MEAN = "accelMean";
    public static final String RC_COLUMN_ACCELEROMETER_SD = "accelSD";
    private HashMap hp;

    public RCDatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + RC_TABLE_NAME + " " +
                        "(" +
                        RC_COLUMN_ID + " INT PRIMARY KEY," +
                        RC_COLUMN_TIMESTAMP + " INT," +
                        RC_COLUMN_ACCURACY + " REAL," +
                        RC_COLUMN_BEARING + " REAL," +
                        RC_COLUMN_LATITUDE + " REAL," +
                        RC_COLUMN_LONGITUDE + " REAL," +
                        RC_COLUMN_SPEED + " REAL," +
                        RC_COLUMN_ACCELEROMETER_MEAN + " REAL," +
                        RC_COLUMN_ACCELEROMETER_SD + " REAL)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + RC_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertSensorData (RCSummarizedData data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RC_COLUMN_TIMESTAMP, data.getTimestamp().getTime());
        contentValues.put(RC_COLUMN_ACCURACY, data.getAccuracy());
        contentValues.put(RC_COLUMN_BEARING, data.getBearing());
        contentValues.put(RC_COLUMN_LATITUDE, data.getLatitude());
        contentValues.put(RC_COLUMN_LONGITUDE, data.getLongitude());
        contentValues.put(RC_COLUMN_SPEED, data.getSpeed());
        contentValues.put(RC_COLUMN_ACCELEROMETER_MEAN, data.getVerticalAccelerometerMean());
        contentValues.put(RC_COLUMN_ACCELEROMETER_SD, data.getVerticalAccelerometerSD());
        db.insert(RC_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getRecords() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                RC_COLUMN_ID,
                RC_COLUMN_TIMESTAMP,
                RC_COLUMN_ACCURACY,
                RC_COLUMN_BEARING,
                RC_COLUMN_LATITUDE,
                RC_COLUMN_LONGITUDE,
                RC_COLUMN_SPEED,
                RC_COLUMN_ACCELEROMETER_MEAN,
                RC_COLUMN_ACCELEROMETER_SD
        };
        Cursor cursor = db.query(RC_TABLE_NAME, projection, null, null, null,null, null);
        cursor.moveToFirst();
        return cursor;
    }
    public static RCSummarizedData getCurrentRecord(Cursor cursor) {
        return new RCSummarizedData(cursor.getLong(0), new Date(cursor.getLong(1)), cursor.getFloat(2), cursor.getFloat(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getFloat(6), cursor.getFloat(7), cursor.getFloat(8));
    }
}