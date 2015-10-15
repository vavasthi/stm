package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.backend.customerApi.model.UserAccount;
import com.khanakirana.khanakirana.KKHashing;
import com.khanakirana.khanakirana.activities.KhanaKiranaMainActivity;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.utils.EndpointManager;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RegisterUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final KhanaKiranaMainActivity context;
    private final String fullname;
    private final String address;
    private final String mobile;
    private final String password;
    private final String city;
    private final String state;
    private final Double latitude;
    private final Double longitude;

    private Logger logger = Logger.getLogger(RegisterUserAsyncTask.class.getName());

    public RegisterUserAsyncTask(KhanaKiranaMainActivity context,
                                 View v) throws NoSuchAlgorithmException {
        this.context = context;
        this.fullname = getValueFromWidget(R.id.fullname);
        this.address = getValueFromWidget(R.id.address);
        this.mobile = getValueFromWidget(R.id.mobile);
        this.password = "PasswordIgnored";
        this.city = getValueFromWidget(R.id.city);
        this.state = getValueFromWidget(R.id.state);
        if (((CheckBox)context.findViewById(R.id.chooseLocation)).isChecked() && context.getRegistrationLocation() != null) {
            this.latitude = context.getRegistrationLocation().getLatitude();
            this.longitude = context.getRegistrationLocation().getLatitude();
        }
        else {

            this.latitude = 0.0;
            this.longitude = 0.0;
        }
    }

    private String getValueFromWidget(int id) {

        EditText e = (EditText)context.findViewById(id);
        return e.getText().toString();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
                UserAccount registeredUser = EndpointManager.getEndpoints(context).register(fullname, address, mobile, city, state, latitude, longitude).execute();
                if (registeredUser != null) {
                    return ServerInteractionReturnStatus.AUTHORIZED;
                } else {
                    return ServerInteractionReturnStatus.REGISTRATION_FAILED;
                }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.AUTHORIZED:
                context.splashMainScreen();
                break;
            case ServerInteractionReturnStatus.REGISTRATION_FAILED:
                context.registrationFailedScreen();
                break;
        }
    }
}
