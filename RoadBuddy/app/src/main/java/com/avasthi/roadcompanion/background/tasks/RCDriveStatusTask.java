package com.avasthi.roadcompanion.background.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Drive;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.activities.RCAbstractActivity;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RCDriveStatusTask extends AsyncTask<Void, Void, PointsOfInterest > {

    private final RCAbstractActivity context;
    private final Long groupId;
    private final Long eventId;
    private final Boolean start;

    private Logger logger = Logger.getLogger(RCDriveStatusTask.class.getName());

    public RCDriveStatusTask(RCAbstractActivity context, Long groupId, Long eventId, boolean start) {
        this.context = context;
        this.eventId = eventId;
        this.groupId = groupId;
        this.start = start;
    }

    public RCDriveStatusTask(RCAbstractActivity context, Long groupId, boolean start) {
        this.context = context;
        this.eventId = -1L;
        this.groupId = groupId;
        this.start = start;
    }
    public RCDriveStatusTask(RCAbstractActivity context, boolean start) {
        this.context = context;
        this.eventId = -1L;
        this.groupId = -1L;
        this.start = start;
    }

    @Override
    protected PointsOfInterest doInBackground(Void... params) {

        try {
            PointsOfInterest pointsOfInterest;
            if (start) {

                pointsOfInterest = EndpointManager.getRoadMeasurementEndpoint(context).startDrive(new DateTime(new Date()),
                        groupId,
                        eventId,
                        RCLocationManager.getInstance().getLastLocation().getLatitude(),
                        RCLocationManager.getInstance().getLastLocation().getLongitude())
                        .execute();
            }
            else {
                pointsOfInterest = EndpointManager.getRoadMeasurementEndpoint(context).finishDrive(RCLocationManager.getInstance().getLastLocation().getLatitude(),
                        RCLocationManager.getInstance().getLastLocation().getLongitude()).execute();
            }
            if (pointsOfInterest != null) {
              return pointsOfInterest;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (PointsOfInterest pointsOfInterest) {

        context.performDriveStatusUpdate(pointsOfInterest);
    }
}
