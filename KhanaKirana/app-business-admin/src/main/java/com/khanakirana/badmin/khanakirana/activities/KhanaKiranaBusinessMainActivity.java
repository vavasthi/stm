package com.khanakirana.badmin.khanakirana.activities;

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.badmin.khanakirana.KKAndroidConstants;
import com.khanakirana.badmin.khanakirana.R;
import com.khanakirana.badmin.khanakirana.background.tasks.AuthenticateUserAsyncTask;
import com.khanakirana.badmin.khanakirana.background.tasks.IsRegisteredUserAsyncTask;
import com.khanakirana.badmin.khanakirana.background.tasks.RegisterUserAsyncTask;
import com.khanakirana.badmin.khanakirana.background.tasks.UpdateBusinessLocationAsyncTask;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Logger;

public class KhanaKiranaBusinessMainActivity extends KhanaKiranaBusinessAbstractActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //    private static GoogleAccountCredential gac;
    private BusinessApi businessApi;
    private Logger logger = Logger.getLogger(KhanaKiranaBusinessMainActivity.class.getName());
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();
        initializeLocationService();
        progressDialog = new ProgressDialog(this);
        businessApi = getEndpoints(this);
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
            case R.id.action_set_business_location:
                if (lastLocation != null) {

                    new UpdateBusinessLocationAsyncTask(this, businessApi, selectedAccountName, lastLocation.getLatitude(), lastLocation.getLongitude()).execute();
                    AlertDialog ad = new AlertDialog.Builder(this).setTitle("Location Alert").setMessage("Location is " + lastLocation.getLatitude() + "," + lastLocation.getLongitude()).show();
                }
                else {

                    AlertDialog ad = new AlertDialog.Builder(this).setTitle("Location Alert").setMessage("Location is not available").show();
                }
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
    public void registerUser(View v) throws NoSuchAlgorithmException {
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

    protected synchronized void initializeLocationService() {
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected() ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }
}
