package com.avasthi.roadcompanion.background.tasks;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RoadCompanionMainBaseActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCDataManager;
import com.avasthi.roadcompanion.utils.RCDatabaseManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.avasthi.roadcompanion.utils.RCSummarizedData;
import com.google.api.client.util.DateTime;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class UploadSensorDataTask extends AsyncTask<Void, Void, PointsOfInterest> {

    private final Context context;
    RCSummarizedData data;

    private Logger logger = Logger.getLogger(UploadSensorDataTask.class.getName());

    public UploadSensorDataTask(Context context,
                                RCSummarizedData data) {
        this.context = context;
        this.data = data;
    }
    @Override
    protected PointsOfInterest doInBackground(Void... params) {

        try {
            PointsOfInterest poi = EndpointManager.getEndpoints(context)
                    .updateSensorData(new DateTime(data.getTimestamp()),
                            data.getVerticalAccelerometerMean(),
                            data.getVerticalAccelerometerSD(),
                            data.getLatitude(),
                            data.getLongitude(),
                            data.getSpeed(),
                            data.getAccuracy(),
                            data.getBearing()).execute();
            if (poi != null) {
                return poi;
            } else {
                return null;
            }
        } catch (Exception e) {
            RCDatabaseManager db = new RCDatabaseManager(context);
            db.insertSensorData(data);
            logger.log(Level.WARNING, "Could not upload data. Storing in database.", e);
        }
        return null;
    }
    protected void onPostExecute (Member member) {

    }
}
