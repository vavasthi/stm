package com.sanjnan.vitarak.app.badmin.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.businessApi.model.BusinessAccountResult;
import com.sanjnan.vitarak.app.badmin.activities.KhanaKiranaBusinessMainActivity;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class UpdateBusinessLocationAsyncTask extends AsyncTask<Void, Void, BusinessAccountResult> {

    private final KhanaKiranaBusinessMainActivity context;
    private final Double latitude;
    private final Double longitude;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public UpdateBusinessLocationAsyncTask(KhanaKiranaBusinessMainActivity context, Double latitude, Double longitude) {
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected BusinessAccountResult doInBackground(Void... params) {

        try {
            BusinessAccountResult registeredUser = EndpointManager.getEndpoints(context).updateLocation(latitude, longitude).execute();
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
