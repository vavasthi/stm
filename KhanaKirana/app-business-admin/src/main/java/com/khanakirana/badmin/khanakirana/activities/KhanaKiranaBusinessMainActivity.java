package com.khanakirana.badmin.khanakirana.activities;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.AccountPicker;
import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.badmin.khanakirana.KKAndroidConstants;
import com.khanakirana.badmin.khanakirana.R;
import com.khanakirana.badmin.khanakirana.background.tasks.AuthenticateUserAsyncTask;
import com.khanakirana.badmin.khanakirana.background.tasks.IsRegisteredUserAsyncTask;
import com.khanakirana.badmin.khanakirana.background.tasks.RegisterUserAsyncTask;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Logger;

public class KhanaKiranaBusinessMainActivity extends KhanaKiranaBusinessAbstractActivity {

    //    private static GoogleAccountCredential gac;
    private BusinessApi businessApi;
    private Logger logger = Logger.getLogger(KhanaKiranaBusinessMainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();
        updateRegistrationLocation();
        progressDialog = new ProgressDialog(this);
        businessApi = getEndpoints();
        telephonyManager = (TelephonyManager)(this.getSystemService(Context.TELEPHONY_SERVICE));
        detectedPhoneNumber = telephonyManager.getLine1Number();
        if (isGoogleAuthentication) {
            if (isRegistered) {
                new AuthenticateUserAsyncTask(this, businessApi, selectedAccountName, Boolean.TRUE, null).execute();
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

                new AuthenticateUserAsyncTask(this, businessApi, selectedAccountName, Boolean.FALSE, password).execute();
            }
            else if (isRegistered) {
                splashReauthenticateScreen();
            }
            else {
                splashRegistrationScreen();
            }
        }
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
        if (selectedAccountName != null) {
            splashRegistrationScreen();
        }
        else {
            // Play services exist but not account not chosen yet..
            chooseAccount();
        }
    }
    private void registerIfRequired() {
        new IsRegisteredUserAsyncTask(this, businessApi, selectedAccountName).execute();
    }
    protected void registerUser(View v) throws NoSuchAlgorithmException {
        progressDialog.show();
        new RegisterUserAsyncTask(this, businessApi, v, isGoogleAuthentication).execute();
    }

    private void setSelectedAccountName(String accountName) {

        isAccountChosen = true;
        selectedAccountName = accountName;
        saveSharedPreferences();
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

}
