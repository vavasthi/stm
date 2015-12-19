package com.avasthi.roadcompanion.background.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.avasthi.android.apps.roadbuddy.backend.registerGoogleCloudMessagingApi.RegisterGoogleCloudMessagingApi;
import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;
import com.avasthi.roadcompanion.activities.RoadCompanionMainBaseActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/12/15.
 */
public class GoogleCloudMessagingRegistrationAsyncTask extends AsyncTask<Context, Void, String> {
    private RegisterGoogleCloudMessagingApi regService;
    private GoogleCloudMessaging gcm;
    private RoadCompanionMainBaseActivity context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "772008650875";

    public GoogleCloudMessagingRegistrationAsyncTask(RoadCompanionMainBaseActivity context) {
        this.context = context;
        regService = EndpointManager.getGoogleCloudMessagingEndpoint(context);
    }

    @Override
    protected String doInBackground(Context... params) {

        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            String regId = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regId;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            regService.register(regId).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        context.splashMainScreen();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}
