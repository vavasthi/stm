package com.avasthi.android.apps.roadbuddy;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.appspot.myapplicationid.roadMeasurementBeanApi.model.RoadMeasurementBean;

/**
 * Created by vavasthi on 29/3/15.
 */
public class MeasurementUploadTask extends AsyncTask<RoadConditionFullscreenActivity, Void, Void> {
    @Override
    protected Void doInBackground(RoadConditionFullscreenActivity... params) {

        while (true) {

            Log.i("UPLOADING", "Uploading records from the database.");
            params[0].getDbHandler().uploadMeasurements();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("SLEEP_INTERRUPTED", "Sleeping thread was interrupted.", e);
            }
        }
    }
}
