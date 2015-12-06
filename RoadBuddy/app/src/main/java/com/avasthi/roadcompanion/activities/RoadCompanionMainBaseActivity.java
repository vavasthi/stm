package com.avasthi.roadcompanion.activities;

/**
 * Created by vavasthi on 22/11/15.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.logging.Logger;

abstract public class RoadCompanionMainBaseActivity extends RCAbstractActivity {

    private Logger logger = Logger.getLogger(RoadCompanionMainBaseActivity.class.getName());

    protected static final String AUTH_PREF = "RCAdminAuthenticationPreferences";
    protected static final String PREF_ACCOUNT_NAME = "RCAdminPreferredAccountName";
    protected static final String PREF_IS_GOOGLE_ACCOUNT = "RCAdminIsGoogleAccount";
    protected static final String PREF_IS_AUTHENTICATED = "RCAdminIsAuthenticated";
    protected static final String PREF_IS_REGISTERED = "RCAdminIsRegistered";
    protected static final String PREF_IS_ACCOUNT_CHOSEN = "RCAdminIsAccountChosen";
    protected static final String PREF_ACCOUNT_PASSWORD = "RCAdminPreferredAccountPassword";
    protected static final String FACEBOOK_INTEGRATED = "RCAFacebookIntegrated";
    protected static Boolean isLoggedIn = null;

    protected static Boolean isAuthenticated = null;
    protected static Boolean isRegistered = null;
    protected static Boolean isAccountChosen = null;
    protected static Boolean isFacebookIntegrated = null;
    protected static SharedPreferences settings;
    protected Member currentMember;

    static String detectedPhoneNumber;
    static TelephonyManager telephonyManager;

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

            EditText email = (EditText) (v.findViewById(R.id.email));
            email.setText(EndpointManager.getAccountName());
        }
        if (detectedPhoneNumber != null) {

            EditText mobile = (EditText) (v.findViewById(R.id.mobile));
            mobile.setText(detectedPhoneNumber);
        }
        EditText city = (EditText) (v.findViewById(R.id.city));
        EditText state = (EditText) (v.findViewById(R.id.state));
        city.setText(RCLocationManager.getInstance().getLastAddress().getLocality());
        state.setText(RCLocationManager.getInstance().getLastAddress().getAdminArea());
        final Button cancel = (Button) (v.findViewById(R.id.cancel));
        final Button register = (Button) (v.findViewById(R.id.register));
    }

    public void facebookInitializationComplete() {

        isFacebookIntegrated = Boolean.TRUE;
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
        isFacebookIntegrated = settings.getBoolean(FACEBOOK_INTEGRATED, Boolean.FALSE);
    }

    protected void saveSharedPreferences() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, EndpointManager.getAccountName());
        editor.putBoolean(PREF_IS_AUTHENTICATED, isAuthenticated);
        editor.putBoolean(PREF_IS_ACCOUNT_CHOSEN, isAccountChosen);
        editor.putBoolean(PREF_IS_REGISTERED, isRegistered);
        editor.putBoolean(FACEBOOK_INTEGRATED, isFacebookIntegrated);
        editor.commit();
    }

    protected boolean isFacebookIntegrated() {
        return isFacebookIntegrated;
    }

    public void setFacebookIntegrated() {
        isFacebookIntegrated = true;
    }

    public static boolean isLoggedIn() {
        if (isLoggedIn != null && isLoggedIn == true) {
            return true;
        }
        return false;
    }

    private void showAlertAndExit(int reason) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(getResources().getString(reason));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RoadCompanionMainBaseActivity.this.finish();
                        dialog.dismiss();
                        System.exit(0);
                    }
                });
        alertDialog.show();
    }

    public void splashMainScreen(Member currentMember) {

        this.currentMember = currentMember;
        isLoggedIn = true;
        hideProgressDialog();
        setContentView(R.layout.road_buddy_main);
        final View v = findViewById(R.id.road_buddy_main_view);
        TextView greeting = (TextView) (v.findViewById(R.id.greeting));
        greeting.setText("Hello " + currentMember.getName() + "!");
        saveSharedPreferences();

    }

    abstract public void continueAuthentication();
}
