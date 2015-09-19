package com.khanakirana.khanakirana;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.khanakirana.backend.userRegistrationApi.UserRegistrationApi;
import com.khanakirana.backend.userRegistrationApi.model.UserAccount;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class IsRegisteredUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final UserRegistrationApi registrationApiService;
    private final String selectedAccountName;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());

    public IsRegisteredUserAsyncTask(KhanaKiranaMainActivity context, UserRegistrationApi registrationApiService, String selectedAccountName) {
        this.context = context;
        this.registrationApiService = registrationApiService;
        this.selectedAccountName = selectedAccountName;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            logger.log(Level.INFO, "Checking if the user is registered." + selectedAccountName);
            UserAccount registeredUser = registrationApiService.isRegisteredUser(selectedAccountName).execute();
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
