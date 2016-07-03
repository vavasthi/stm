package com.sanjnan.vitarak.app.badmin.tasks;

import android.os.AsyncTask;

import com.sanjnan.vitarak.app.badmin.activities.SanjnanBusinessMainActivity;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;
import com.sanjnan.vitarak.server.backend.businessApi.model.BusinessAccountResult;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final SanjnanBusinessMainActivity context;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(SanjnanBusinessMainActivity context) {
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            BusinessAccountResult accountResult = EndpointManager.getEndpoints(context).isRegisteredUser().execute();
            if (accountResult != null) {
                return ServerInteractionReturnStatus.SUCCESS;
            } else {
                return ServerInteractionReturnStatus.AUTHENTICATION_FAILED;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
            return ServerInteractionReturnStatus.AUTHENTICATION_FAILED;
        }
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.AUTHENTICATION_FAILED:
                context.splashRegistrationScreen();
                break;
            case ServerInteractionReturnStatus.SUCCESS:
                context.splashMainScreen();
                break;
        }
    }
}
