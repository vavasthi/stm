package com.sanjnan.vitarak.app.badmin.tasks;

import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sanjnan.vitarak.app.badmin.R;
import com.sanjnan.vitarak.app.badmin.activities.SanjnanBusinessMainActivity;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;
import com.sanjnan.vitarak.app.badmin.utils.SanjnanLocationManager;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;
import com.sanjnan.vitarak.server.backend.businessApi.model.BusinessAccountResult;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RegisterUserAsyncTask extends AsyncTask<Void, Void, Integer> {

    private final SanjnanBusinessMainActivity context;
    private final String fullname;
    private final String address;
    private final String mobile;
    private final String city;
    private final String state;
    private final Double latitude;
    private final Double longitude;

    private Logger logger = Logger.getLogger(RegisterUserAsyncTask.class.getName());

    public RegisterUserAsyncTask(SanjnanBusinessMainActivity context,
                                 View v) throws NoSuchAlgorithmException {
        this.context = context;
        this.fullname = getValueFromWidget(R.id.fullname);
        this.address = getValueFromWidget(R.id.address);
        this.mobile = getValueFromWidget(R.id.mobile);
        this.city = getValueFromWidget(R.id.city);
        this.state = getValueFromWidget(R.id.state);
        Location l = SanjnanLocationManager.getInstance().getLastLocation();
        if (((CheckBox)context.findViewById(R.id.chooseLocation)).isChecked() && l != null) {
            this.latitude = l.getLatitude();
            this.longitude = l.getLatitude();
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
                BusinessAccountResult registeredUser
                        = EndpointManager.getEndpoints(context).register(fullname, address, mobile, city, state, latitude, longitude).execute();
                if (registeredUser != null) {
                    return ServerInteractionReturnStatus.SUCCESS;
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
            case ServerInteractionReturnStatus.SUCCESS:
                context.splashMainScreen();
                break;
            case ServerInteractionReturnStatus.REGISTRATION_FAILED:
                context.registrationFailedScreen();
                break;
        }
    }
}
