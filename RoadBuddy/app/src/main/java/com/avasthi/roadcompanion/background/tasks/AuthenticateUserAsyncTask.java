package com.avasthi.roadcompanion.background.tasks;

/**
 * Created by vavasthi on 22/11/15.
 */

import android.os.AsyncTask;


import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.MemberAndVehicles;
import com.avasthi.roadcompanion.activities.RoadCompanionMainBaseActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, MemberAndVehicles > {

    private final RoadCompanionMainBaseActivity context;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(RoadCompanionMainBaseActivity context) {
        this.context = context;
    }

    @Override
    protected MemberAndVehicles doInBackground(Void... params) {

        try {
            MemberAndVehicles memberAndVehicles = EndpointManager.getRoadMeasurementEndpoint(context).isRegisteredUser().execute();
            if (memberAndVehicles != null) {

                RCLocationManager.getInstance().setCurrentMemberAndVehicles(memberAndVehicles);
            }
            return memberAndVehicles;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
        }
        return null;
    }
    protected void onPostExecute (MemberAndVehicles memberAndVehicles) {

        if (memberAndVehicles == null) {
            // The user is not registered. Splash registration screen.
            context.splashRegistrationScreen();
        }
        else {

            new GoogleCloudMessagingRegistrationAsyncTask(context).execute();
        }
    }
}
