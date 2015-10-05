package com.khanakirana.badmin.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.backend.businessApi.model.BusinessAccount;
import com.khanakirana.badmin.khanakirana.KhanaKiranaMainActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class IsRegisteredUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final BusinessApi businessApi;
    private final String selectedAccountName;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public IsRegisteredUserAsyncTask(KhanaKiranaMainActivity context, BusinessApi businessApi, String selectedAccountName) {
        this.context = context;
        this.businessApi = businessApi;
        this.selectedAccountName = selectedAccountName;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            logger.log(Level.INFO, "Checking if the user is registered." + selectedAccountName);
            BusinessAccount registeredUser = businessApi.isRegisteredUser(selectedAccountName).execute();
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
