package com.avasthi.android.apps.roadbuddy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import com.appspot.myapplicationid.roadMeasurementBeanApi.model.RoadMeasurementBean;
import com.google.api.client.util.DateTime;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "measurementDb";

    // Contacts table name
    private static final String TABLE_MEASUREMENT = "measurements";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";

    private RoadConditionFullscreenActivity activity;
    public DatabaseHandler(RoadConditionFullscreenActivity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
        this.activity = activity;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEASUREMENTS_TABLE = "CREATE TABLE " + TABLE_MEASUREMENT + "(";
        CREATE_MEASUREMENTS_TABLE += "timestamp integer,";
        CREATE_MEASUREMENTS_TABLE += "accuracy real,";
        CREATE_MEASUREMENTS_TABLE += "altitude real,";
        CREATE_MEASUREMENTS_TABLE += "bearing real,";
        CREATE_MEASUREMENTS_TABLE += "latitude real,";
        CREATE_MEASUREMENTS_TABLE += "longitude real,";
        CREATE_MEASUREMENTS_TABLE += "speed real,";
        CREATE_MEASUREMENTS_TABLE += "accelX real,";
        CREATE_MEASUREMENTS_TABLE += "accelY real,";
        CREATE_MEASUREMENTS_TABLE += "accelZ real,";
        CREATE_MEASUREMENTS_TABLE += "accelXDev real,";
        CREATE_MEASUREMENTS_TABLE += "accelYDev real,";
        CREATE_MEASUREMENTS_TABLE += "accelZDev real)";
        db.execSQL(CREATE_MEASUREMENTS_TABLE);
        Log.i("CREATING_TABLE", "Database table created :" + CREATE_MEASUREMENTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public synchronized void addMeasurement(RoadMeasurementBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("timestamp", bean.getTimestamp().getValue());
        values.put("accuracy", bean.getAccuracy());
        values.put("altitude", bean.getAltitude());
        values.put("bearing",bean.getBearing());
        values.put("latitude", bean.getLatitude());
        values.put("longitude", bean.getLongitude());
        values.put("speed", bean.getSpeed());
        values.put("accelX", bean.getAccelX());
        values.put("accelY", bean.getAccelY());
        values.put("accelZ", bean.getAccelZ());
        values.put("accelXDev", bean.getAccelXDev());
        values.put("accelYDev", bean.getAccelYDev());
        values.put("accelZDev", bean.getAccelZDev());

        // Inserting Row
        db.insert(TABLE_MEASUREMENT, null, values);
        db.close(); // Closing database connection
    }
    // Getting All Contacts
    public synchronized void  uploadMeasurements() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREMENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int successfulUploads = 0;
        int failedUploads = 0;
        // looping through all rows and adding to list
        List<Long> toBeDeletedTimestamps = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Map<String, Integer> columnPosition = new HashMap<>();
            for (int i = 0; i < cursor.getColumnCount(); ++i) {
                columnPosition.put(cursor.getColumnName(i), i);
            }
            do {

                final RoadMeasurementBean bean = new RoadMeasurementBean();
                long ts = cursor.getLong(columnPosition.get("timestamp"));
                bean.setTimestamp(new DateTime(new Date(ts)));
                bean.setAccuracy(cursor.getDouble(columnPosition.get("accuracy")));
                bean.setAltitude(cursor.getDouble(columnPosition.get("altitude")));
                bean.setBearing(cursor.getFloat(columnPosition.get("bearing")));
                bean.setLatitude(cursor.getDouble(columnPosition.get("latitude")));
                bean.setLongitude(cursor.getDouble(columnPosition.get("longitude")));
                bean.setSpeed(cursor.getFloat(columnPosition.get("speed")));
                bean.setAccelX(cursor.getFloat(columnPosition.get("accelX")));
                bean.setAccelY(cursor.getFloat(columnPosition.get("accelY")));
                bean.setAccelZ(cursor.getFloat(columnPosition.get("accelZ")));
                bean.setAccelXDev(cursor.getFloat(columnPosition.get("accelXDev")));
                bean.setAccelYDev(cursor.getFloat(columnPosition.get("accelYDev")));
                bean.setAccelZDev(cursor.getFloat(columnPosition.get("accelZDev")));
                try {
                    if (bean.getSpeed() > -1) {

                        activity.getApi().insertRoadMeasurementBean(bean).execute();
                        ++successfulUploads;
                        final int finalSuccessfulUploads = successfulUploads;
                        activity.findViewById(R.id.top_content_frame).post(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(activity, bean);
                                if (finalSuccessfulUploads > 0) {

                                    Toast.makeText(activity, "Uploaded " + finalSuccessfulUploads + " measures.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    toBeDeletedTimestamps.add(ts);
                } catch (IOException e) {
                    Log.e("UPLOAD_FAILED", "Upload of measurement to datastore failed.", e);
                    e.printStackTrace();
                    ++failedUploads;
                    break;
                }
            } while (cursor.moveToNext());
            for (long ts : toBeDeletedTimestamps) {

                db.delete(TABLE_MEASUREMENT, "timestamp = ?",new String[] { String.valueOf(ts) });
            }
        }

        Log.i("UPLOAD_SUCCESSFUL", successfulUploads + " measurements uploaded to datastore " + failedUploads + " failed.");
        db.close();
    }
    private void updateUI(Activity activity, RoadMeasurementBean bean) {
        GridLayout gl = (GridLayout) activity.findViewById(R.id.top_content_frame);
        EditText accelx = (EditText)gl.findViewById(R.id.acceleration_x);
        accelx.setText(" " + bean.getAccelXDev());
        EditText accely = (EditText)gl.findViewById(R.id.acceleration_y);
        accely.setText(" " + bean.getAccelYDev());
        EditText accelz = (EditText)gl.findViewById(R.id.acceleration_z);
        accelz.setText(" " + bean.getAccelZDev());
        EditText speed = (EditText)gl.findViewById(R.id.speed);
        speed.setText(" " + bean.getSpeed());
        EditText latitude = (EditText)gl.findViewById(R.id.latitude);
        latitude.setText(" " + bean.getLatitude());
        EditText longitude = (EditText)gl.findViewById(R.id.longitude);
        longitude.setText(" " + bean.getLongitude());
    }
}