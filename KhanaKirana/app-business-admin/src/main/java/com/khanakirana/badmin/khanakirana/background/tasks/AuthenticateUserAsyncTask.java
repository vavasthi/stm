package com.khanakirana.badmin.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.backend.businessApi.model.BusinessAccount;
import com.khanakirana.backend.businessApi.model.BusinessAccountResult;
import com.khanakirana.badmin.khanakirana.activities.KhanaKiranaBusinessMainActivity;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, BusinessAccountResult> {

    private final KhanaKiranaBusinessMainActivity context;
    private final BusinessApi businessApiService;
    private final String selectedAccountName;
    private final String password;
    private final Boolean isGoogleAccount;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(KhanaKiranaBusinessMainActivity context,
                                     BusinessApi businessApiService,
                                     String selectedAccountName,
                                     Boolean isGoogleAccount, String password) {
        this.context = context;
        this.businessApiService = businessApiService;
        this.selectedAccountName = selectedAccountName;
        this.isGoogleAccount = isGoogleAccount;
        this.password = password;
    }

    @Override
    protected BusinessAccountResult doInBackground(Void... params) {

        try {
            if (isGoogleAccount) {

                BusinessAccountResult registeredUser = businessApiService.isRegisteredUser(selectedAccountName).execute();
                System.out.println("Registered user is :" + registeredUser.toString());
                if (registeredUser != null) {
                    return registeredUser;
                } else {
                    return null;
                }
            } else {
                BusinessAccountResult registeredUser = businessApiService.authenticate(selectedAccountName, password, isGoogleAccount).execute();
                if (registeredUser != null) {
                    return registeredUser;
                }
                else {
                    return null;
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
        }
        return null;
    }
    protected void onPostExecute (BusinessAccountResult account) {

        context.splashAppropriateViewForAccount(account);
    }
}
