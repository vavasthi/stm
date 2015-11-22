package com.avasthi.roadcompanion.background.tasks;

/**
 * Created by vavasthi on 22/11/15.
 */

import android.os.AsyncTask;


import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.roadcompanion.activities.RoadCompanionAbstractActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, Member > {

    private final RoadCompanionAbstractActivity context;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(RoadCompanionAbstractActivity context) {
        this.context = context;
    }

    @Override
    protected Member doInBackground(Void... params) {

        try {
            Member member = EndpointManager.getEndpoints(context).isRegisteredUser().execute();
            return member;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
        }
        return null;
    }
    protected void onPostExecute (Member member) {

        if (member == null) {
            // The user is not registered. Splash registration screen.
            context.splashRegistrationScreen();
        }
        context.splashMainScreen();
    }
}
