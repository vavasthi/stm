package com.sanjnan.vitarak.app.badmin.activities;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.sanjnan.vitarak.app.badmin.SanjnanAndroidConstants;
import com.sanjnan.vitarak.app.badmin.tasks.AuthenticateUserAsyncTask;
import com.sanjnan.vitarak.app.badmin.tasks.RegisterUserAsyncTask;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;
import com.sanjnan.vitarak.app.badmin.utils.PermissionRequestConstants;
import com.sanjnan.vitarak.app.badmin.utils.SanjnanLocationManager;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.logging.Logger;

import static android.Manifest.*;

public class SanjnanBusinessMainActivity extends SanjnanBusinessAbstractActivity {

    //    private static GoogleAccountCredential gac;
    private Logger logger = Logger.getLogger(SanjnanBusinessMainActivity.class.getName());
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PermissionRequestConstants.REQUEST_PERMISSION_FOR_SELF_PHONE_NUMBER: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    detectedPhoneNumber = telephonyManager.getLine1Number();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PermissionRequestConstants.ACCOUNTS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();
        SanjnanLocationManager.initialize(this, Locale.getDefault());
        progressDialog = new ProgressDialog(this);
        int permissionRequest = 0;
        telephonyManager = (TelephonyManager)(this.getSystemService(Context.TELEPHONY_SERVICE));
        if (ContextCompat.checkSelfPermission(this, permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission.READ_SMS, permission.READ_PHONE_STATE},
                    PermissionRequestConstants.REQUEST_PERMISSION_FOR_SELF_PHONE_NUMBER);
        }
        if (ContextCompat.checkSelfPermission(this, permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission.GET_ACCOUNTS},
                    PermissionRequestConstants.ACCOUNTS_PERMISSION);
        }
        if (isRegistered) {
            new AuthenticateUserAsyncTask(this).execute();
        }
        else if (isAccountChosen) {
            registerIfRequired();
        }
        else {
            chooseAccount();
        }
    }


    private void registerIfRequired() {
        splashRegistrationScreen();
    }

    public void registerUser(View v) throws NoSuchAlgorithmException {
        new RegisterUserAsyncTask(this, v).execute();
    }

    private void chooseAccount() {

        String[] accountTypes = new String[]{"com.google"};
        Intent  intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
        // Play services exist but not account not chosen yet..
        startActivityForResult(intent, SanjnanAndroidConstants.REQUEST_ACCOUNT_PICKER);
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
    private void setSelectedAccountName(String accountName) {

        isAccountChosen = true;
        EndpointManager.setAccountName(accountName);
        saveSharedPreferences();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SanjnanAndroidConstants.REQUEST_ACCOUNT_PICKER :
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setSelectedAccountName(accountName);
                        // User is selected, we can splash registration screen now.
                        splashRegistrationScreen();
                    }
                }
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (SanjnanLocationManager.getInstance() != null) {

            SanjnanLocationManager.getInstance().pause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (SanjnanLocationManager.getInstance() != null) {

            SanjnanLocationManager.getInstance().resume();
        }
    }

}
