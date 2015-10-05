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
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final CustomerApi customerApi;
    private final String selectedAccountName;
    private final String password;
    private final Boolean isGoogleAccount;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(KhanaKiranaMainActivity context,
                                     CustomerApi customerApi,
                                     String selectedAccountName,
                                     Boolean isGoogleAccount, String password) {
        this.context = context;
        this.customerApi = customerApi;
        this.selectedAccountName = selectedAccountName;
        this.isGoogleAccount = isGoogleAccount;
        this.password = password;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            if (isGoogleAccount) {

                UserAccount registeredUser = customerApi.isRegisteredUser(selectedAccountName).execute();
                System.out.println("Registered user is :" + registeredUser.toString());
                if (registeredUser != null) {
                    return ServerInteractionReturnStatus.AUTHORIZED;
                } else {
                    return ServerInteractionReturnStatus.AUTHENTICATED_BUT_NOT_REGISTERED;
                }
            } else {
                UserAccount registeredUser = customerApi.authenticate(selectedAccountName, password, isGoogleAccount).execute();
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
