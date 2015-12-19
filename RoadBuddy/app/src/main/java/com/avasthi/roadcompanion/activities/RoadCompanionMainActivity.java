package com.avasthi.roadcompanion.activities;


import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Drive;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Vehicle;
import com.avasthi.roadcompanion.background.tasks.GoogleCloudMessagingRegistrationAsyncTask;
import com.avasthi.roadcompanion.background.tasks.RCDriveStatusTask;
import com.avasthi.roadcompanion.utils.Constants;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.background.tasks.AuthenticateUserAsyncTask;
import com.avasthi.roadcompanion.background.tasks.RegisterUserAsyncTask;
import com.avasthi.roadcompanion.service.RCDataCollectorService;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.avasthi.roadcompanion.utils.RCSensorManager;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.AccountPicker;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.logging.Logger;

public class RoadCompanionMainActivity extends RoadCompanionMainBaseActivity {


    private Logger logger = Logger.getLogger(RoadCompanionMainActivity.class.getName());
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        loadSharedPreferences();
        RCLocationManager.initialize(this, Locale.ENGLISH);
        telephonyManager = (TelephonyManager)(this.getSystemService(Context.TELEPHONY_SERVICE));
        detectedPhoneNumber = telephonyManager.getLine1Number();
        // FacebookReadInterface.initialize(this);
        // FacebookPublishInterface.initialize(this);
        continueAuthentication();
    }

    public void continueAuthentication() {

        if (isAccountChosen) {
            new AuthenticateUserAsyncTask(this).execute();
        }
        else if (!isAccountChosen) {
            chooseAccount();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
            case R.id.groups:
                startActivityForResult(new Intent(this, RCGroupActivity.class), Constants.GROUP_ACTIVITY);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseAccount() {

        String[] accountTypes = new String[]{"com.google"};
        Intent  intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
        // Play services exist but not account not chosen yet..
        startActivityForResult(intent, Constants.REQUEST_ACCOUNT_PICKER);
    }
    private void obtainCredentials() {
        setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        if (EndpointManager.getAccountName() != null) {
            splashRegistrationScreen();
        }
        else {
            // Play services exist but no account not chosen yet..
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
        super.onActivityResult(requestCode, resultCode, data);
/*        if (FacebookReadInterface.isInitialied()) {

            FacebookReadInterface.getInstance().processActivityResult(requestCode, resultCode, data);
        }*/
        switch (requestCode) {
            case Constants.REQUEST_ACCOUNT_PICKER :
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setSelectedAccountName(accountName);
                        // User is selected, we can splash registration screen now.
                        new AuthenticateUserAsyncTask(this).execute();
                    }
                }
                break;
            case Constants.GROUP_ACTIVITY:
                break;
        }
    }

    public static String getDetectedPhoneNumber() {

        if (detectedPhoneNumber == null) {
            detectedPhoneNumber = telephonyManager.getLine1Number();
        }
        return detectedPhoneNumber;
    }

    public void registerUser(View v) throws NoSuchAlgorithmException {
        showProgressDialog(this);
        new RegisterUserAsyncTask(this, v).execute();
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        teardownDataCollectorService();
        //FacebookReadInterface.fini();
    }
    public void mainScreenAction(View v) {

        boolean serviceRunning = isServiceRunning(RCDataCollectorService.class);
        if (serviceRunning) {
            showProgressDialog(this);
            stopCollectionService();
            new RCDriveStatusTask(this, Boolean.FALSE).execute(); // Stop a drive
        }
        else {
            ListView lv = (ListView)findViewById(R.id.vehicle_list_view);
            if (selectedVehiclePosition == -1) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.select_a_vehicle)
                        .setMessage(R.string.select_a_vehicle_msg)
                        .show();
                return;
            }
            showProgressDialog(this);
            Vehicle vehicle =  RCLocationManager.getInstance().getCurrentMemberAndVehicles().getVehicles().get(selectedVehiclePosition);
            startCollectionService();
            new RCDriveStatusTask(this, Boolean.TRUE).execute(); // Start a drive
        }
        setMainScreenActionResource(!serviceRunning);
    }
    public void performDriveStatusUpdate(PointsOfInterest pointsOfInterest) {
        super.performDriveStatusUpdate(pointsOfInterest);
        boolean serviceRunning = isServiceRunning(RCDataCollectorService.class);
        if (serviceRunning && pointsOfInterest.getCurrentDrive() != null && !pointsOfInterest.getCurrentDrive().getDone()) {
            // Service and drive is still running.
        }
        hideProgressDialog();

    }
}