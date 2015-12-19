package com.avasthi.roadcompanion.utils;

import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by vavasthi on 13/12/15.
 */
public class RCDatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RoadCompanion.db";
    public static final String RC_SENSOR_DATA_TABLE_NAME = "SensorData";
    public static final String RC_SD_COLUMN_ID = "_ID";
    public static final String RC_SD_COLUMN_TIMESTAMP = "timestamp";
    public static final String RC_SD_COLUMN_USERID = "userId";
    public static final String RC_SD_COLUMN_DRIVEID = "driveId";
    public static final String RC_SD_COLUMN_ACCURACY = "accuracy";
    public static final String RC_SD_COLUMN_BEARING = "bearing";
    public static final String RC_SD_COLUMN_LATITUDE = "latitude";
    public static final String RC_SD_COLUMN_LONGITUDE = "longitude";
    public static final String RC_SD_COLUMN_SPEED = "speed";
    public static final String RC_SD_COLUMN_ACCELEROMETER_MEAN = "accelMean";
    public static final String RC_SD_COLUMN_ACCELEROMETER_SD = "accelSD";
    public static final String RC_SD_COLUMN_RECORD_UPLOADED = "uploaded";
    private HashMap hp;

    public RCDatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + RC_SENSOR_DATA_TABLE_NAME + " " +
                        "(" +
                        RC_SD_COLUMN_ID + " INTEGER PRIMARY KEY," +
                        RC_SD_COLUMN_TIMESTAMP + " INTEGER," +
                        RC_SD_COLUMN_USERID + " INTEGER," +
                        RC_SD_COLUMN_DRIVEID + " INTEGER," +
                        RC_SD_COLUMN_ACCURACY + " REAL," +
                        RC_SD_COLUMN_BEARING + " REAL," +
                        RC_SD_COLUMN_LATITUDE + " REAL," +
                        RC_SD_COLUMN_LONGITUDE + " REAL," +
                        RC_SD_COLUMN_SPEED + " REAL," +
                        RC_SD_COLUMN_ACCELEROMETER_MEAN + " REAL," +
                        RC_SD_COLUMN_ACCELEROMETER_SD + " REAL," +
                        RC_SD_COLUMN_RECORD_UPLOADED + " INTEGER)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + RC_SENSOR_DATA_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertSensorData (RCSummarizedData data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RC_SD_COLUMN_TIMESTAMP, data.getTimestamp().getTime());
        contentValues.put(RC_SD_COLUMN_USERID, data.getMemberId());
        contentValues.put(RC_SD_COLUMN_DRIVEID, data.getDriveId());
        contentValues.put(RC_SD_COLUMN_ACCURACY, data.getAccuracy());
        contentValues.put(RC_SD_COLUMN_BEARING, data.getBearing());
        contentValues.put(RC_SD_COLUMN_LATITUDE, data.getLatitude());
        contentValues.put(RC_SD_COLUMN_LONGITUDE, data.getLongitude());
        contentValues.put(RC_SD_COLUMN_SPEED, data.getSpeed());
        contentValues.put(RC_SD_COLUMN_ACCELEROMETER_MEAN, data.getVerticalAccelerometerMean());
        contentValues.put(RC_SD_COLUMN_ACCELEROMETER_SD, data.getVerticalAccelerometerSD());
        contentValues.put(RC_SD_COLUMN_RECORD_UPLOADED, 0);
        db.insert(RC_SENSOR_DATA_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getRecords() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                RC_SD_COLUMN_ID,
                RC_SD_COLUMN_TIMESTAMP,
                RC_SD_COLUMN_USERID,
                RC_SD_COLUMN_DRIVEID,
                RC_SD_COLUMN_ACCURACY,
                RC_SD_COLUMN_BEARING,
                RC_SD_COLUMN_LATITUDE,
                RC_SD_COLUMN_LONGITUDE,
                RC_SD_COLUMN_SPEED,
                RC_SD_COLUMN_ACCELEROMETER_MEAN,
                RC_SD_COLUMN_ACCELEROMETER_SD
        };
        Cursor cursor = db.query(RC_SENSOR_DATA_TABLE_NAME, projection, null, null, null,null, null);
        cursor.moveToFirst();
        return cursor;
    }
    public static RCSummarizedData getCurrentRecord(Cursor cursor) {
        return new RCSummarizedData(cursor.getLong(0),
                new Date(cursor.getLong(1)),
                cursor.getLong(2),
                cursor.getLong(3),
                cursor.getFloat(4),
                cursor.getFloat(5),
                cursor.getDouble(6),
                cursor.getDouble(7),
                cursor.getFloat(8),
                cursor.getFloat(9),
                cursor.getFloat(10),
                cursor.getFloat(11));
    }
}