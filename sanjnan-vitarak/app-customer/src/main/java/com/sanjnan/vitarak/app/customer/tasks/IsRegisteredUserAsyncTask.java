package com.sanjnan.vitarak.app.customer.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.customerApi.model.UserAccount;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;
import com.sanjnan.vitarak.app.customer.activities.KhanaKiranaMainActivity;
import com.sanjnan.vitarak.app.customer.utils.EndpointManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class IsRegisteredUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public IsRegisteredUserAsyncTask(KhanaKiranaMainActivity context) {
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            logger.log(Level.INFO, "Checking if the user is registered." + EndpointManager.getAccountName());
            UserAccount registeredUser = EndpointManager.getEndpoints(context).isRegisteredUser().execute();
            if (registeredUser != null) {
                logger.info("Registered user is :" + registeredUser.toString() );
                return ServerInteractionReturnStatus.SUCCESS;
            }
            else {
                return ServerInteractionReturnStatus.AUTHENTICATION_FAILED;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call" + e.getCause().getMessage(), e);
        }
        return ServerInteractionReturnStatus.AUTHENTICATION_FAILED;
    }
    protected void onPostExecute (Integer result) {

        if (result == ServerInteractionReturnStatus.AUTHENTICATION_FAILED) {
            context.splashRegistrationScreen();
        }
        else {
            context.splashMainScreen();
        }
    }
}
