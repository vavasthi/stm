package com.khanakirana.admin.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.admin.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.khanakirana.backend.sysadminApi.model.SysadminAccount;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class IsRegisteredUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final SysadminApi sysadminApi;
    private final String selectedAccountName;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public IsRegisteredUserAsyncTask(KhanaKiranaMainActivity context, SysadminApi sysadminApi, String selectedAccountName) {
        this.context = context;
        this.sysadminApi = sysadminApi;
        this.selectedAccountName = selectedAccountName;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            logger.log(Level.INFO, "Checking if the user is registered." + selectedAccountName);
            SysadminAccount registeredUser = sysadminApi.isRegisteredUser(selectedAccountName).execute();
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
