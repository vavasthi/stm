package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;

import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.ObjectWithStatus;
import com.khanakirana.backend.sysadminApi.model.Actionable;
import com.khanakirana.backend.sysadminApi.model.SysadminAccount;
import com.avasthi.roadcompanion.common.ServerInteractionReturnStatus;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, ObjectWithStatus<List<Actionable> > > {

    private final RoadCompanionMainActivity context;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(RoadCompanionMainActivity context) {
        this.context = context;
    }

    @Override
    protected ObjectWithStatus<List<Actionable> > doInBackground(Void... params) {

        try {
            SysadminAccount registeredUser = EndpointManager.getEndpoints(context).isRegisteredUser().execute();
            if (registeredUser != null) {
                List<Actionable> actionables = EndpointManager.getEndpoints(context).getActionables().execute().getItems();
                ObjectWithStatus<List<Actionable> > returnValue = new ObjectWithStatus<>(actionables, ServerInteractionReturnStatus.SUCCESS);
                return returnValue;
            }
            return new ObjectWithStatus<>(null, ServerInteractionReturnStatus.INVALID_USER);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
        }
        return new ObjectWithStatus<>(null, ServerInteractionReturnStatus.FATAL_ERROR);
    }
    protected void onPostExecute (ObjectWithStatus<List<Actionable>> result) {

        context.splashAppropriateViewForAccount(result);
    }
}
