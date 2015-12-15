package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.activities.RCTollActivity;
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
public class RCAddTollStopTask extends AsyncTask<Void, Void, Toll > {

    private final RCTollActivity context;
    private Boolean onlyTollStop;
    private Boolean fasTagLane;
    private Toll toll;
    private float amount;

    private Logger logger = Logger.getLogger(RCAddTollStopTask.class.getName());

    public RCAddTollStopTask(RCTollActivity context, Toll toll, Boolean fasTagLane, Float amount) {
        this.context = context;
        this.amount = amount;
        this.fasTagLane = fasTagLane;
        onlyTollStop = true;
    }
    public RCAddTollStopTask(RCTollActivity context, Boolean fasTagLane, Float amount) {
        this.context = context;
        this.amount = amount;
        this.fasTagLane = fasTagLane;
        onlyTollStop = false;
    }

    @Override
    protected Toll doInBackground(Void... params) {

        Toll returnedToll = null;
        try {
            if (onlyTollStop) {

                returnedToll = EndpointManager.getRoadMeasurementEndpoint(context).addTollStop(toll.getId(),
                        new DateTime(new Date()),
                        fasTagLane,
                        amount).execute();
            }
            else {

                returnedToll = EndpointManager.getRoadMeasurementEndpoint(context).addToll(new DateTime(new Date()),
                        fasTagLane,
                        RCLocationManager.getInstance().getLastLocation().getLatitude(),
                        RCLocationManager.getInstance().getLastLocation().getLongitude(),
                        amount,
                        RCLocationManager.getInstance().getLastAddress().getLocality(),
                        RCLocationManager.getInstance().getLastAddress().getAdminArea(),
                        RCLocationManager.getInstance().getLastAddress().getCountryCode()).execute();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return returnedToll;
    }
    protected void onPostExecute (Toll toll) {
        context.finish();
    }
}
