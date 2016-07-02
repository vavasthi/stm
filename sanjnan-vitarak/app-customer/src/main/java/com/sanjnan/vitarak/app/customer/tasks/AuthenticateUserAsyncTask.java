package com.sanjnan.vitarak.app.customer.tasks;

import android.os.AsyncTask;

import com.khanakirana.backend.customerApi.model.UserAccount;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;
import com.sanjnan.vitarak.app.customer.activities.KhanaKiranaMainActivity;
import com.sanjnan.vitarak.app.customer.utils.EndpointManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final String password;
    private final Boolean isGoogleAccount;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(KhanaKiranaMainActivity context,
                                     Boolean isGoogleAccount, String password) {
        this.context = context;
        this.isGoogleAccount = isGoogleAccount;
        this.password = password;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            UserAccount registeredUser = EndpointManager.getEndpoints(context).isRegisteredUser().execute();
            System.out.println("Registered user is :" + registeredUser.toString());
            if (registeredUser != null) {
                return ServerInteractionReturnStatus.SUCCESS;
            } else {
                return ServerInteractionReturnStatus.AUTHENTICATION_FAILED;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
            return ServerInteractionReturnStatus.AUTHENTICATION_FAILED;
        }
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.AUTHENTICATION_FAILED:
                context.splashRegistrationScreen();
                break;
            case ServerInteractionReturnStatus.SUCCESS:
                context.splashMainScreen();
                break;
            case ServerInteractionReturnStatus.INVALID_USER:
                context.reauthorizeUserScreen();
                break;
        }
    }
}
