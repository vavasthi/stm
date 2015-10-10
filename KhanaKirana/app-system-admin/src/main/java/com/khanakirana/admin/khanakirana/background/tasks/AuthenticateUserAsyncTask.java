package com.khanakirana.admin.khanakirana.background.tasks;

import android.os.AsyncTask;

import com.khanakirana.admin.khanakirana.activities.KhanaKiranaMainAdminActivity;
import com.khanakirana.admin.khanakirana.utils.ObjectWithStatus;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.khanakirana.backend.sysadminApi.model.Actionable;
import com.khanakirana.backend.sysadminApi.model.SysadminAccount;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AuthenticateUserAsyncTask extends AsyncTask<Void, Void, ObjectWithStatus<List<Actionable> > > {

    private final KhanaKiranaMainAdminActivity context;
    private final SysadminApi sysadminApi;
    private final String selectedAccountName;
    private final String password;
    private final Boolean isGoogleAccount;

    private Logger logger = Logger.getLogger(AuthenticateUserAsyncTask.class.getName());


    public AuthenticateUserAsyncTask(KhanaKiranaMainAdminActivity context,
                                     SysadminApi sysadminApi,
                                     String selectedAccountName,
                                     Boolean isGoogleAccount, String password) {
        this.context = context;
        this.sysadminApi = sysadminApi;
        this.selectedAccountName = selectedAccountName;
        this.isGoogleAccount = isGoogleAccount;
        this.password = password;
    }

    @Override
    protected ObjectWithStatus<List<Actionable> > doInBackground(Void... params) {

        try {
            SysadminAccount registeredUser = sysadminApi.isRegisteredUser(selectedAccountName).execute();
            if (registeredUser != null) {
                List<Actionable> actionables = sysadminApi.getActionables().execute().getItems();
                ObjectWithStatus<List<Actionable> > returnValue = new ObjectWithStatus<>(actionables, ServerInteractionReturnStatus.SUCCESS);
                return returnValue;
            }
            return new ObjectWithStatus<>(null, ServerInteractionReturnStatus.INVALID_USER);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failure in remote call ", e);
        }
        return new ObjectWithStatus<>(null, ServerInteractionReturnStatus.FATAL_ERROR);
    }
    protected void onPostExecute (ObjectWithStatus<List<Actionable>> result) {

        context.splashAppropriateViewForAccount(result);
    }
}
