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
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final BusinessApi businessApiService;
    private final String selectedAccountName;
    private final String password;
    private final Boolean isGoogleAccount;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(KhanaKiranaMainActivity context,
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
    protected Integer doInBackground(Void... params) {

        try {
            if (isGoogleAccount) {

                BusinessAccount registeredUser = businessApiService.isRegisteredUser(selectedAccountName).execute();
                System.out.println("Registered user is :" + registeredUser.toString());
                if (registeredUser != null) {
                    return ServerInteractionReturnStatus.AUTHORIZED;
                } else {
                    return ServerInteractionReturnStatus.AUTHENTICATED_BUT_NOT_REGISTERED;
                }
            } else {
                BusinessAccount registeredUser = businessApiService.authenticate(selectedAccountName, password, isGoogleAccount).execute();
                if (registeredUser != null) {
                    logger.info(registeredUser.toPrettyString());
                    return ServerInteractionReturnStatus.AUTHORIZED;
                }
                else {
                    return ServerInteractionReturnStatus.INVALID_USER;
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.AUTHENTICATED_BUT_NOT_REGISTERED:
                context.splashRegistrationScreen();
                break;
            case ServerInteractionReturnStatus.AUTHORIZED:
                context.splashMainScreen();
                break;
            case ServerInteractionReturnStatus.INVALID_USER:
                context.reauthorizeUserScreen();
                break;
        }
    }
}
