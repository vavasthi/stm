package com.avasthi.roadcompanion.background.tasks;

import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RoadCompanionMainBaseActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.FacebookReadInterface;
import com.avasthi.roadcompanion.utils.RCLocationManager;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RegisterUserAsyncTask extends AsyncTask<Void, Void, Member> {

    private final RoadCompanionMainBaseActivity context;
    private final String name;
    private final String mobile;
    private final String city;
    private final String state;
    private final String detectedCity;
    private final String detectedState;
    private final Double latitude;
    private final Double longitude;

    private Logger logger = Logger.getLogger(RegisterUserAsyncTask.class.getName());

    public RegisterUserAsyncTask(RoadCompanionMainBaseActivity context,
                                 View v) throws NoSuchAlgorithmException {
        this.context = context;
        this.name = getValueFromWidget(R.id.fullname);
        this.mobile = getValueFromWidget(R.id.mobile);
        this.city = getValueFromWidget(R.id.city);
        this.state = getValueFromWidget(R.id.state);
        Address lastAddress = RCLocationManager.getInstance().getLastAddress();
        detectedCity = lastAddress.getLocality();
        detectedState = lastAddress.getAdminArea();
        Location lastLocation = RCLocationManager.getInstance().getLastLocation();
        this.latitude = lastLocation.getLatitude();
        this.longitude = lastLocation.getLongitude();
    }

    private String getValueFromWidget(int id) {

        EditText e = (EditText)context.findViewById(id);
        return e.getText().toString();
    }

    @Override
    protected Member doInBackground(Void... params) {

        try {
            Member registeredUser = EndpointManager.getEndpoints(context).register(name,
                    mobile,
                    city,
                    state,
                    detectedCity,
                    detectedState,
                    latitude,
                    longitude,
                    FacebookReadInterface.getInstance().getFacebookProfile().getId()
            ).execute();
            if (registeredUser != null) {
                return registeredUser;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (Member member) {

        context.splashMainScreen(member);
    }
}
