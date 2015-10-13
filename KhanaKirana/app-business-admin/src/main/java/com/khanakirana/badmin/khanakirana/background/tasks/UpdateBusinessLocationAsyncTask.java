package com.khanakirana.badmin.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.backend.businessApi.model.BusinessAccountResult;
import com.khanakirana.badmin.khanakirana.activities.KhanaKiranaBusinessMainActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class UpdateBusinessLocationAsyncTask extends AsyncTask<Void, Void, BusinessAccountResult> {

    private final KhanaKiranaBusinessMainActivity context;
    private final BusinessApi businessApi;
    private final String selectedAccountName;
    private final Double latitude;
    private final Double longitude;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public UpdateBusinessLocationAsyncTask(KhanaKiranaBusinessMainActivity context, BusinessApi businessApi, String selectedAccountName, Double latitude, Double longitude) {
        this.context = context;
        this.businessApi = businessApi;
        this.selectedAccountName = selectedAccountName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected BusinessAccountResult doInBackground(Void... params) {

        try {
            logger.log(Level.INFO, "Checking if the user is registered." + selectedAccountName);
            BusinessAccountResult registeredUser = businessApi.updateLocation(latitude, longitude).execute();
            if (registeredUser != null) {
                logger.info("Registered user is :" + registeredUser.toString() );
                return registeredUser;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call" + e.getCause().getMessage(), e);
        }
        return null;
    }
    protected void onPostExecute (BusinessAccountResult account) {

        context.splashAppropriateViewForAccount(account);
    }
}
