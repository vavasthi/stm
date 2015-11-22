package com.avasthi.roadcompanion.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.khanakirana.admin.khanakirana.R;
import com.avasthi.roadcompanion.adapters.KKRecyclerViewMainScreenAdapter;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.ObjectWithStatus;
import com.khanakirana.backend.sysadminApi.model.Actionable;
import com.avasthi.roadcompanion.common.KKActionTypes;
import com.avasthi.roadcompanion.common.KKStringCodes;
import com.avasthi.roadcompanion.common.ServerInteractionReturnStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 7/10/15.
 */
abstract class RoadCompanionAbstractActivity extends Activity {

    private Logger logger = Logger.getLogger(RoadCompanionAbstractActivity.class.getName());

    protected static final String AUTH_PREF = "KKAdminAuthenticationPreferences";
    protected static final String PREF_ACCOUNT_NAME = "KKAdminPreferredAccountName";
    protected static final String PREF_IS_GOOGLE_ACCOUNT = "KKAdminIsGoogleAccount";
    protected static final String PREF_IS_AUTHENTICATED = "KKAdminIsAuthenticated";
    protected static final String PREF_IS_REGISTERED = "KKAdminIsRegistered";
    protected static final String PREF_IS_ACCOUNT_CHOSEN = "KKAdminIsAccountChosen";
    protected static final String PREF_ACCOUNT_PASSWORD = "KKAdminPreferredAccountPassword";
    protected static Boolean isGoogleAuthentication = null;
    protected static String password = null;
    protected static Boolean isAuthenticated = null;
    protected static Boolean isRegistered = null;
    protected static Boolean isAccountChosen = null;
    protected static SharedPreferences settings;
    static String detectedPhoneNumber;
    static TelephonyManager telephonyManager;
    Location registrationLocation;
    ProgressDialog progressDialog;

    public void splashMainScreen(List<Actionable> actionables) {
        isRegistered = true;
        saveSharedPreferences();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {

                    progressDialog.hide();
                }
            }
        });
        setContentView(R.layout.activity_khana_kirana_sysadmin_main);
        RecyclerView rv = (RecyclerView)findViewById(R.id.kk_sysadmin_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        if (actionables == null || actionables.size() == 0) {
            actionables = new ArrayList<>();
            Actionable actionable = new Actionable();
            actionable.setDone(Boolean.TRUE);
            actionable.setActionType(KKActionTypes.NOTHING_TO_DO);
            actionable.setTitle(KKStringCodes.ACTIONABLE_NOTHING_TO_DO_TITLE);
            actionable.setDescription(KKStringCodes.ACTIONABLE_NOTHING_TO_DO_DESCRIPTION);;
            actionables.add(actionable);
        }
        rv.setAdapter(new KKRecyclerViewMainScreenAdapter(this, actionables.toArray(new Actionable[actionables.size()])));
    }

    public void splashUserNotAdminScreen() {
        isRegistered = true;
        saveSharedPreferences();
        setContentView(R.layout.not_valid_admin_user);
    }

    public void splashReauthenticateScreen() {

    }

    public void splashFatalErrorScreen() {

    }

    public void splashBadUserScreen() {

    }

    public void splashRegistrationScreen() {

        if (isGoogleAuthentication) {

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
        } else {

            setContentView(R.layout.registration_nongoogle_user);
            final View v = findViewById(R.id.registration_nongoogle_user_view);
            if (EndpointManager.getAccountName() != null) {

                EditText email = (EditText) (v.findViewById(R.id.email));
                email.setText(EndpointManager.getAccountName());
            }
            if (detectedPhoneNumber != null) {

                EditText mobile = (EditText) (v.findViewById(R.id.mobile));
                mobile.setText(detectedPhoneNumber);
            }
        }
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
        isGoogleAuthentication = settings.getBoolean(PREF_IS_GOOGLE_ACCOUNT, isGoogleAuthenticationDefault);
        isAuthenticated = settings.getBoolean(PREF_IS_AUTHENTICATED, Boolean.FALSE);
        isRegistered = settings.getBoolean(PREF_IS_REGISTERED, Boolean.FALSE);
        isAccountChosen = settings.getBoolean(PREF_IS_ACCOUNT_CHOSEN, Boolean.FALSE);
        password = settings.getString(PREF_ACCOUNT_PASSWORD, null);
        logger.info("Loading shared preferences : GA = " + isGoogleAuthentication + " Authed = " + isAuthenticated + " ACChosen = " + isAccountChosen + " Regd :" + isRegistered);
    }

    protected void saveSharedPreferences() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, EndpointManager.getAccountName());
        editor.putBoolean(PREF_IS_GOOGLE_ACCOUNT, isGoogleAuthentication);
        editor.putBoolean(PREF_IS_AUTHENTICATED, isAuthenticated);
        editor.putBoolean(PREF_IS_ACCOUNT_CHOSEN, isAccountChosen);
        editor.putBoolean(PREF_IS_REGISTERED, isRegistered);
        editor.putString(PREF_ACCOUNT_PASSWORD, password);
        editor.commit();
    }


    public void splashAppropriateViewForAccount(final ObjectWithStatus<List<Actionable>> ows) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                switch (ows.getStatus()) {
                    case ServerInteractionReturnStatus.SUCCESS:
                        splashMainScreen(ows.getObject());
                        break;
                    case ServerInteractionReturnStatus.INVALID_USER:
                        splashUserNotAdminScreen();
                        break;
                    default:
                        splashFatalErrorScreen();
                        break;
                }
            }
        });
    }

}
