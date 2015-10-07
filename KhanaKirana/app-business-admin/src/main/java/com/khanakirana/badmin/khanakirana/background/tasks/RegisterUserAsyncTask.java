package com.khanakirana.badmin.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.backend.businessApi.model.BusinessAccount;
import com.khanakirana.backend.businessApi.model.BusinessAccountResult;
import com.khanakirana.badmin.khanakirana.KKHashing;
import com.khanakirana.badmin.khanakirana.activities.KhanaKiranaBusinessMainActivity;
import com.khanakirana.badmin.khanakirana.R;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RegisterUserAsyncTask extends AsyncTask<Void, Void, BusinessAccountResult> {

    private final KhanaKiranaBusinessMainActivity context;
    private final BusinessApi businessApi;
    private final String name;
    private final String address;
    private final String email;
    private final String mobile;
    private final String password;
    private final String city;
    private final String state;
    private final Double latitude;
    private final Double longitude;
    private final Boolean isGoogleAccount;

    private Logger logger = Logger.getLogger(RegisterUserAsyncTask.class.getName());

    public RegisterUserAsyncTask(KhanaKiranaBusinessMainActivity context,
                                 BusinessApi businessApi,
                                 View v,
                                 Boolean isGoogleAccount) throws NoSuchAlgorithmException {
        this.context = context;
        this.businessApi = businessApi;
        this.name = getValueFromWidget(R.id.fullname);
        this.address = getValueFromWidget(R.id.address);
        this.email = getValueFromWidget(R.id.email);
        this.mobile = getValueFromWidget(R.id.mobile);
        this.isGoogleAccount = isGoogleAccount;
        if (!isGoogleAccount) {

            this.password = KKHashing.hashPassword(getValueFromWidget(R.id.password));
        }
        else {
            this.password = "PasswordIgnored";
        }
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
    protected BusinessAccountResult doInBackground(Void... params) {

        try {
            if (isGoogleAccount) {

                BusinessAccountResult registeredUser = businessApi.register(name, address, email, mobile, password, city, state, latitude, longitude, Boolean.TRUE).execute();
                if (registeredUser != null) {
                    return registeredUser;
                } else {
                    return null;
                }
            } else {
                BusinessAccountResult registeredUser = businessApi.register(name, address, email, mobile, password, city, state, latitude, longitude, Boolean.FALSE).execute();
                if (registeredUser != null) {
                    return registeredUser;
                }
                else {
                    return null;
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (BusinessAccountResult account) {

        context.splashAppropriateViewForAccount(account);
    }
}
