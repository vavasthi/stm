package com.avasthi.roadcompanion.background.tasks;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Drive;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCDatabaseManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.avasthi.roadcompanion.utils.RCSummarizedData;
import com.google.api.client.util.DateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class UploadSensorDataTask extends AsyncTask<Void, Void, PointsOfInterest> {

    private final Context context;
    private final RCSummarizedData data;

    private Logger logger = Logger.getLogger(UploadSensorDataTask.class.getName());

    public UploadSensorDataTask(Context context,
                                RCSummarizedData data) {
        this.context = context;
        this.data = data;
    }
    @Override
    protected PointsOfInterest doInBackground(Void... params) {

        try {
            Double lastLatitude = RCLocationManager.getInstance().getPointsOfInterest().getCurrentDrive().getLastLatitude();
            Double lastLongitude = RCLocationManager.getInstance().getPointsOfInterest().getCurrentDrive().getLastLongitude();
            float[] distance = new float[3];
            Location.distanceBetween(lastLatitude, lastLongitude, data.getLatitude(), data.getLongitude(), distance);
            PointsOfInterest poi = EndpointManager.getRoadMeasurementEndpoint(context)
                    .updateSensorData(new DateTime(data.getTimestamp()),
                            data.getVerticalAccelerometerMean(),
                            data.getVerticalAccelerometerSD(),
                            data.getLatitude(),
                            data.getLongitude(),
                            data.getSpeed(),
                            data.getAccuracy(),
                            data.getBearing(),
                            distance[0]).execute();
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
    protected void onPostExecute (PointsOfInterest poi) {
        if (RCLocationManager.getInstance() != null) {

            RCLocationManager.getInstance().updatePointsOfInterest(poi);
        }
    }
}
