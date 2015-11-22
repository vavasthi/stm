package com.avasthi.roadcompanion.activities;

/**
 * Created by vavasthi on 22/11/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

abstract public class RoadCompanionAbstractActivity extends Activity {

    private Logger logger = Logger.getLogger(RoadCompanionAbstractActivity.class.getName());

    protected static final String AUTH_PREF = "RCAdminAuthenticationPreferences";
    protected static final String PREF_ACCOUNT_NAME = "RCAdminPreferredAccountName";
    protected static final String PREF_IS_GOOGLE_ACCOUNT = "RCAdminIsGoogleAccount";
    protected static final String PREF_IS_AUTHENTICATED = "RCAdminIsAuthenticated";
    protected static final String PREF_IS_REGISTERED = "RCAdminIsRegistered";
    protected static final String PREF_IS_ACCOUNT_CHOSEN = "RCAdminIsAccountChosen";
    protected static final String PREF_ACCOUNT_PASSWORD = "RCAdminPreferredAccountPassword";

    protected static Boolean isAuthenticated = null;
    protected static Boolean isRegistered = null;
    protected static Boolean isAccountChosen = null;
    protected static SharedPreferences settings;
    static String detectedPhoneNumber;
    static TelephonyManager telephonyManager;
    Location registrationLocation;
    ProgressDialog progressDialog;

    public void splashReauthenticateScreen() {

    }

    public void splashFatalErrorScreen() {

    }

    public void splashBadUserScreen() {

    }

    public void splashMainScreen() {

        hideProgressDialog();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    protected void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
    public void splashRegistrationScreen() {

        setContentView(R.layout.registration_google_user);
        final View v = findViewById(R.id.registration_google_user_view);
        if (EndpointManager.getAccountName() != null) {

            EditText email = (EditText) (v.findViewById(R.id.email));
            email.setText(EndpointManager.getAccountName());
        }
        if (detectedPhoneNumber != null) {

            EditText mobile = (EditText) (v.findViewById(R.id.mobile));
            mobile.setText(detectedPhoneNumber);
        }
    }

    public static String getDetectedPhoneNumber() {

        if (detectedPhoneNumber == null) {
            detectedPhoneNumber = telephonyManager.getLine1Number();
        }
        return detectedPhoneNumber;
    }

    protected void loadSharedPreferences() {

        boolean isGoogleAuthenticationDefault = false;

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            isGoogleAuthenticationDefault = true;
        }
        settings = getSharedPreferences(AUTH_PREF, 0);
        EndpointManager.setAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        isAuthenticated = settings.getBoolean(PREF_IS_AUTHENTICATED, Boolean.FALSE);
        isRegistered = settings.getBoolean(PREF_IS_REGISTERED, Boolean.FALSE);
        isAccountChosen = settings.getBoolean(PREF_IS_ACCOUNT_CHOSEN, Boolean.FALSE);
    }

    protected void saveSharedPreferences() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, EndpointManager.getAccountName());
        editor.putBoolean(PREF_IS_AUTHENTICATED, isAuthenticated);
        editor.putBoolean(PREF_IS_ACCOUNT_CHOSEN, isAccountChosen);
        editor.putBoolean(PREF_IS_REGISTERED, isRegistered);
        editor.commit();
    }
}
