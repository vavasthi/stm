package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.backend.customerApi.model.UserAccount;
import com.khanakirana.khanakirana.activities.KhanaKiranaMainActivity;
import com.khanakirana.khanakirana.utils.EndpointManager;

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
                return ServerInteractionReturnStatus.ALREADY_REGISTERED;
            }
            else {
                return ServerInteractionReturnStatus.AUTHENTICATED_BUT_NOT_REGISTERED;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call" + e.getCause().getMessage(), e);
        }
        return ServerInteractionReturnStatus.AUTHENTICATED_BUT_NOT_REGISTERED;
    }
    protected void onPostExecute (Integer result) {

        if (result == ServerInteractionReturnStatus.AUTHENTICATED_BUT_NOT_REGISTERED) {
            context.splashRegistrationScreen();
        }
        else {
            context.splashMainScreen();
        }
    }
}
