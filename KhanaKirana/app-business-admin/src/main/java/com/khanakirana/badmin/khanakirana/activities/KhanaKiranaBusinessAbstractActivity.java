package com.khanakirana.badmin.khanakirana.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.backend.businessApi.model.BusinessAccountResult;
import com.khanakirana.badmin.khanakirana.KKAndroidConstants;
import com.khanakirana.badmin.khanakirana.R;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 7/10/15.
 */
abstract class KhanaKiranaBusinessAbstractActivity extends Activity {

    private Logger logger = Logger.getLogger(KhanaKiranaBusinessAbstractActivity.class.getName());

    protected static final String AUTH_PREF = "KKBusinessAuthenticationPreferences";
    protected static final String PREF_ACCOUNT_NAME = "KKBusinessPreferredAccountName";
    protected static final String PREF_IS_GOOGLE_ACCOUNT = "KKBusinessIsGoogleAccount";
    protected static final String PREF_IS_AUTHENTICATED = "KKBusinessIsAuthenticated";
    protected static final String PREF_IS_REGISTERED = "KKBusinessIsRegistered";
    protected static final String PREF_IS_ACCOUNT_CHOSEN = "KKBusinessIsAccountChosen";
    protected static final String PREF_ACCOUNT_PASSWORD = "KKBusinessPreferredAccountPassword";
    protected static final String AUDIENCE = "server:client_id:279496868246-7lvjvi7tsoi4dt88bfsmagt9j04ar32u.apps.googleusercontent.com";
    static String selectedAccountName = null;
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

    public void splashMainScreen() {
        isRegistered = true;
        saveSharedPreferences();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.hide();
            }
        });
        setContentView(R.layout.activity_khana_kirana_main);
    }
    public void splashUserNotApprovedScreen() {
        isRegistered = true;
        saveSharedPreferences();
        setContentView(R.layout.activity_khana_kirana_main);
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
            if (selectedAccountName != null) {

                EditText email = (EditText)(v.findViewById(R.id.email));
                email.setText(selectedAccountName);
            }
            if (detectedPhoneNumber != null) {

                EditText mobile = (EditText)(v.findViewById(R.id.mobile));
                mobile.setText(detectedPhoneNumber);
            }
        }
        else {

            setContentView(R.layout.registration_nongoogle_user);
            final View v = findViewById(R.id.registration_nongoogle_user_view);
            if (selectedAccountName != null) {

                EditText email = (EditText)(v.findViewById(R.id.email));
                email.setText(selectedAccountName);
            }
            if (detectedPhoneNumber != null) {

                EditText mobile = (EditText)(v.findViewById(R.id.mobile));
                mobile.setText(detectedPhoneNumber);
            }
        }
    }

    public static String getSelectedAccountName() {
        return selectedAccountName;
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
        selectedAccountName = settings.getString(PREF_ACCOUNT_NAME, null);
        isGoogleAuthentication = settings.getBoolean(PREF_IS_GOOGLE_ACCOUNT, isGoogleAuthenticationDefault);
        isAuthenticated = settings.getBoolean(PREF_IS_AUTHENTICATED, Boolean.FALSE);
        isRegistered = settings.getBoolean(PREF_IS_REGISTERED, Boolean.FALSE);
        isAccountChosen = settings.getBoolean(PREF_IS_ACCOUNT_CHOSEN, Boolean.FALSE);
        password = settings.getString(PREF_ACCOUNT_PASSWORD, null);
        logger.info("Loading shared preferences : GA = " + isGoogleAuthentication + " Authed = " + isAuthenticated + " ACChosen = " + isAccountChosen + " Regd :" + isRegistered);
    }
    protected void saveSharedPreferences() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, selectedAccountName);
        editor.putBoolean(PREF_IS_GOOGLE_ACCOUNT, isGoogleAuthentication);
        editor.putBoolean(PREF_IS_AUTHENTICATED, isAuthenticated);
        editor.putBoolean(PREF_IS_ACCOUNT_CHOSEN, isAccountChosen);
        editor.putBoolean(PREF_IS_REGISTERED, isRegistered);
        editor.putString(PREF_ACCOUNT_PASSWORD, password);
        editor.commit();
//        gac.setSelectedAccountName(selectedAccountName);
    }

    public static BusinessApi getEndpoints() {

        // Create API handler
        HttpRequestInitializer requestInitializer = getRequestInitializer();
        BusinessApi.Builder builder = new BusinessApi.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                requestInitializer)
                .setRootUrl(KKAndroidConstants.ROOT_URL)
                .setGoogleClientRequestInitializer(
                        new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(
                                    final AbstractGoogleClientRequest<?>
                                            abstractGoogleClientRequest)
                                    throws IOException {
                                abstractGoogleClientRequest
                                        .setDisableGZipContent(true);
                            }
                        }
                );

        return builder.build();
    }
    /**
     * Returns appropriate HttpRequestInitializer depending whether the
     * application is configured to require users to be signed in or not.
     * @return an appropriate HttpRequestInitializer.
     */
    public static HttpRequestInitializer getRequestInitializer() {
        return null;
/*        if (KKAndroidConstants.SIGN_IN_REQUIRED) {
//            return SignInActivity.getCredential();
            return new HttpRequestInitializer() {
                @Override
                public void initialize(final HttpRequest arg0) {
                }
            };

        } else {
            return new HttpRequestInitializer() {
                @Override
                public void initialize(final HttpRequest arg0) {
                }
            };
        }*/
    }



    public void splashAppropriateViewForAccount(final BusinessAccountResult bar) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (bar == null) {
                    splashUserNotApprovedScreen();
                }
                else {
                    if (!isRegistered ) {
                        splashRegistrationScreen();
                    }
                    else {
                        if (bar.getAccount() == null) {
                            splashRegistrationScreen();
                        }
                        else {
                            switch(bar.getStatus()) {
                                case ServerInteractionReturnStatus.REGISTRATION_FAILED:
                                    Toast.makeText(KhanaKiranaBusinessAbstractActivity.this, R.string.kk_registration_failed_message, Toast.LENGTH_LONG);
                                    splashRegistrationScreen();
                                    break;
                                case ServerInteractionReturnStatus.SUCCESS:
                                    splashMainScreen();
                                    break;
                                case ServerInteractionReturnStatus.INVALID_USER:
                                    splashBadUserScreen();
                                    break;
                                case ServerInteractionReturnStatus.AUTHENTICATION_FAILED:
                                    splashReauthenticateScreen();
                                    break;
                                case ServerInteractionReturnStatus.FATAL_ERROR:
                                    splashFatalErrorScreen();
                                    break;
                            }
                        }
                    }
                }

            }
        });
    }

}
