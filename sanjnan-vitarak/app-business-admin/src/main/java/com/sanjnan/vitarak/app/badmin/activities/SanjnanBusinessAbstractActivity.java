package com.sanjnan.vitarak.app.badmin.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sanjnan.vitarak.app.badmin.R;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;

import java.util.logging.Logger;


/**
 * Created by vavasthi on 7/10/15.
 */
abstract class SanjnanBusinessAbstractActivity extends Activity {

    private Logger logger = Logger.getLogger(SanjnanBusinessAbstractActivity.class.getName());

    protected static final String AUTH_PREF = "SanjnanBusinessAuthenticationPreferences";
    protected static final String PREF_ACCOUNT_NAME = "SanjnanBusinessPreferredAccountName";
    protected static final String PREF_IS_AUTHENTICATED = "SanjnanBusinessIsAuthenticated";
    protected static final String PREF_IS_REGISTERED = "SanjnanBusinessIsRegistered";
    protected static final String PREF_IS_ACCOUNT_CHOSEN = "SanjnanBusinessIsAccountChosen";
    protected static final String PREF_ACCOUNT_PASSWORD = "SanjnanBusinessPreferredAccountPassword";
    protected static String password = null;
    protected static Boolean isAuthenticated = null;
    protected static Boolean isRegistered = null;
    protected static Boolean isAccountChosen = null;
    protected static SharedPreferences settings;
    static String detectedPhoneNumber = null;
    static TelephonyManager telephonyManager;
    Location registrationLocation;
    ProgressDialog progressDialog;

    public void splashMainScreen() {
        isRegistered = true;
        saveSharedPreferences();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.hide();
            }
        });
        setContentView(R.layout.activity_sanjnan_business_main);
    }
    public void splashUserNotApprovedScreen() {
        isRegistered = true;
        saveSharedPreferences();
        setContentView(R.layout.business_user_not_approved);
    }
    public void splashReauthenticateScreen() {

    }
    public void splashFatalErrorScreen() {

    }
    public void splashBadUserScreen() {

    }
    public void splashRegistrationScreen() {

        setContentView(R.layout.registration_google_user);
        final View v = findViewById(R.id.registration_google_user_view);
        if (EndpointManager.getAccountName() != null) {

            EditText email = (EditText)(v.findViewById(R.id.email));
            email.setText(EndpointManager.getAccountName());
        }
        if (detectedPhoneNumber != null) {

            EditText mobile = (EditText)(v.findViewById(R.id.mobile));
            mobile.setText(detectedPhoneNumber);
        }
    }
    public void registrationFailedScreen() {
    }

    public static String getDetectedPhoneNumber() {

        if (detectedPhoneNumber == null) {
            detectedPhoneNumber = telephonyManager.getLine1Number();
        }
        return detectedPhoneNumber;
    }
    public Location getRegistrationLocation() {
        return registrationLocation;
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
        password = settings.getString(PREF_ACCOUNT_PASSWORD, null);
    }
    protected void saveSharedPreferences() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, EndpointManager.getAccountName());
        editor.putBoolean(PREF_IS_AUTHENTICATED, isAuthenticated);
        editor.putBoolean(PREF_IS_ACCOUNT_CHOSEN, isAccountChosen);
        editor.putBoolean(PREF_IS_REGISTERED, isRegistered);
        editor.putString(PREF_ACCOUNT_PASSWORD, password);
        editor.commit();
//        gac.setSelectedAccountName(selectedAccountName);
    }

}
