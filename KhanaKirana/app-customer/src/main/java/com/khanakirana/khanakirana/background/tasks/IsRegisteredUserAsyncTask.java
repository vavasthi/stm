package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.backend.customerApi.model.UserAccount;
import com.khanakirana.khanakirana.KhanaKiranaMainActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class IsRegisteredUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final CustomerApi customerApi;
    private final String selectedAccountName;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public IsRegisteredUserAsyncTask(KhanaKiranaMainActivity context, CustomerApi customerApi, String selectedAccountName) {
        this.context = context;
        this.customerApi = customerApi;
        this.selectedAccountName = selectedAccountName;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            logger.log(Level.INFO, "Checking if the user is registered." + selectedAccountName);
            UserAccount registeredUser = customerApi.isRegisteredUser(selectedAccountName).execute();
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
