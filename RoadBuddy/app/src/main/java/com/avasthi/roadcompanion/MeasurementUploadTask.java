package com.avasthi.roadcompanion;

import android.os.AsyncTask;
import android.util.Log;

import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;


/**
 * Created by vavasthi on 29/3/15.
 */
public class MeasurementUploadTask extends AsyncTask<RoadCompanionMainActivity, Void, Void> {
    @Override
    protected Void doInBackground(RoadCompanionMainActivity... params) {

        while (true) {

            Log.i("UPLOADING", "Uploading records from the database.");
        }
    }
}
