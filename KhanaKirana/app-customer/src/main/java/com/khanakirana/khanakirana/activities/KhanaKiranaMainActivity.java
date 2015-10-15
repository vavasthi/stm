package com.khanakirana.khanakirana.activities;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.khanakirana.KKAndroidConstants;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.background.tasks.AuthenticateUserAsyncTask;
import com.khanakirana.khanakirana.background.tasks.IsRegisteredUserAsyncTask;
import com.khanakirana.khanakirana.background.tasks.RegisterUserAsyncTask;
import com.khanakirana.khanakirana.utils.EndpointManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Logger;

public class KhanaKiranaMainActivity extends Activity {

    private static final String AUTH_PREF = "KKAuthenticationPreferences";
    private static final String PREF_ACCOUNT_NAME = "KKPreferredAccountName";
    private static final String PREF_IS_GOOGLE_ACCOUNT = "KKIsGoogleAccount";
    private static final String PREF_IS_AUTHENTICATED = "KKIsAuthenticated";
    private static final String PREF_IS_REGISTERED = "KKIsRegistered";
    private static final String PREF_IS_ACCOUNT_CHOSEN = "KKIsAccountChosen";
    private static final String PREF_ACCOUNT_PASSWORD = "KKPreferredAccountPassword";
    private static final String AUDIENCE = "server:client_id:279496868246-7lvjvi7tsoi4dt88bfsmagt9j04ar32u.apps.googleusercontent.com";
    private static Boolean isGoogleAuthentication = null;
    private static String password = null;
    private static Boolean isAuthenticated = null;
    private static Boolean isRegistered = null;
    private static Boolean isAccountChosen = null;
    private static SharedPreferences settings;
    //    private static GoogleAccountCredential gac;
    static String detectedPhoneNumber;
    static TelephonyManager telephonyManager;
    Location registrationLocation;

    private Logger logger = Logger.getLogger(KhanaKiranaMainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();
        updateRegistrationLocation();
        telephonyManager = (TelephonyManager)(this.getSystemService(Context.TELEPHONY_SERVICE));
        detectedPhoneNumber = telephonyManager.getLine1Number();
        if (isGoogleAuthentication) {
            if (isRegistered) {
                new AuthenticateUserAsyncTask(this, Boolean.TRUE, null).execute();
            }
            else if (isAccountChosen) {
                registerIfRequired();
            }
            else {
                chooseAccount();
            }
        }
        else {
            if (isAuthenticated) {

                new AuthenticateUserAsyncTask(this, Boolean.FALSE, password).execute();
            }
            else if (isRegistered) {
                reauthorizeUserScreen();
            }
            else {
                splashRegistrationScreen();
            }
        }
    }


    private void loadSharedPreferences() {

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
    private void saveSharedPreferences() {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, EndpointManager.getAccountName());
        editor.putBoolean(PREF_IS_GOOGLE_ACCOUNT, isGoogleAuthentication);
        editor.putBoolean(PREF_IS_AUTHENTICATED, isAuthenticated);
        editor.putBoolean(PREF_IS_ACCOUNT_CHOSEN, isAccountChosen);
        editor.putBoolean(PREF_IS_REGISTERED, isRegistered);
        editor.putString(PREF_ACCOUNT_PASSWORD, password);
        editor.commit();
//        gac.setSelectedAccountName(selectedAccountName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_khana_kirana_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.reset:
                settings.edit().clear().commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseAccount() {

        String[] accountTypes = new String[]{"com.google"};
        Intent  intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
            // Play services exist but not account not chosen yet..
        startActivityForResult(intent, KKAndroidConstants.REQUEST_ACCOUNT_PICKER);
    }
    private void obtainCredentials() {
        setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        if (EndpointManager.getAccountName()!= null) {
            splashRegistrationScreen();
        }
        else {
            // Play services exist but not account not chosen yet..
            chooseAccount();
        }
    }
    private void registerIfRequired() {
        new IsRegisteredUserAsyncTask(this).execute();
    }
    void registerUser(View v) throws NoSuchAlgorithmException {
        new RegisterUserAsyncTask(this, v).execute();
    }

    private void setSelectedAccountName(String accountName) {

        isAccountChosen = true;
        EndpointManager.setAccountName(accountName);
        saveSharedPreferences();
    }

    public void splashMainScreen() {
        isRegistered = true;
        saveSharedPreferences();
        setContentView(R.layout.activity_khana_kirana_main);
    }
    public void reauthorizeUserScreen() {

    }
    public void registrationFailedScreen() {

    }
    public void splashRegistrationScreen() {

        if (isGoogleAuthentication) {

            setContentView(R.layout.registration_google_user);
            final View v = findViewById(R.id.registration_google_user_view);
            final KhanaKiranaMainActivity context = this;
            if (EndpointManager.getAccountName() != null) {

                EditText email = (EditText)(v.findViewById(R.id.email));
                email.setText(EndpointManager.getAccountName());
            }
            if (detectedPhoneNumber != null) {

                EditText mobile = (EditText)(v.findViewById(R.id.mobile));
                mobile.setText(detectedPhoneNumber);
            }
            final Button button = (Button)findViewById(R.id.register);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        context.registerUser(v);
                    } catch (NoSuchAlgorithmException e) {
                        registrationFailedScreen();
                    }
                }
            });
        }
        else {

            setContentView(R.layout.registration_nongoogle_user);
            final View v = findViewById(R.id.registration_nongoogle_user_view);
            if (EndpointManager.getAccountName() != null) {

                EditText email = (EditText)(v.findViewById(R.id.email));
                email.setText(EndpointManager.getAccountName());
            }
            if (detectedPhoneNumber != null) {

                EditText mobile = (EditText)(v.findViewById(R.id.mobile));
                mobile.setText(detectedPhoneNumber);
            }
            final KhanaKiranaMainActivity context = this;
            final Button button = (Button)findViewById(R.id.register);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        context.registerUser(v);
                    } catch (NoSuchAlgorithmException e) {
                        registrationFailedScreen();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case KKAndroidConstants.REQUEST_ACCOUNT_PICKER :
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setSelectedAccountName(accountName);
                        isGoogleAuthentication = Boolean.TRUE;
                        // User is selected, we can splash registration screen now.
                        splashRegistrationScreen();
                    }
                }
                break;
        }
    }

    private void updateRegistrationLocation() {

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location;
        try {

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {

                registrationLocation = location;
            } else if (location != null && (Calendar.getInstance().getTimeInMillis() - location.getTime()) > 2 * 60 * 1000) {

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        registrationLocation = location;
                        try {

                            locationManager.removeUpdates(this);
                        }
                        catch(SecurityException ex) {

                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }, null);
            }
        } catch(SecurityException e) {

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
}
