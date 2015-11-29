package com.avasthi.roadcompanion.activities;


import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avasthi.roadcompanion.Constants;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.background.tasks.AuthenticateUserAsyncTask;
import com.avasthi.roadcompanion.background.tasks.RegisterUserAsyncTask;
import com.avasthi.roadcompanion.data.GroupMenu;
import com.avasthi.roadcompanion.data.GroupMenuItem;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.FacebookPublishInterface;
import com.avasthi.roadcompanion.utils.FacebookReadInterface;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.AccountPicker;
import com.google.common.base.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
        FacebookReadInterface.initialize(this);
        FacebookPublishInterface.initialize(this);
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
            case R.id.force_facebook_login:
                FacebookReadInterface.getInstance().forceRelogin();
                return true;
/*            case R.id.reset:
                settings.edit().clear().commit();
                break;
            case R.id.add_item:
                startActivityForResult(new Intent(this, KKAddProductInMasterListActivity.class), KKAndroidConstants.CAMERA_REQUEST);
                break;
            case R.id.adding_measurement_category_popup_item:
                startActivityForResult(new Intent(this, com.khanakirana.admin.khanakirana.activities.KKAddMeasurementCategoryActivity.class), KKAndroidConstants.ADD_MEASUREMENT_CATEGORY_REQUEST);
                break;
            case R.id.adding_measurement_unit_popup_item:
                startActivityForResult(new Intent(this, com.khanakirana.admin.khanakirana.activities.KKAddMeasurementUnitActivity.class), KKAndroidConstants.ADD_MEASUREMENT_UNIT_REQUEST);
                break;
            case R.id.add_item_category:
                startActivityForResult(new Intent(this, com.khanakirana.admin.khanakirana.activities.KKManageItemCategoryActivity.class), KKAndroidConstants.ADD_ITEM_CATEGORY_REQUEST);
                break;
            case R.id.kk_set_business_polygon:
                Intent intent = new Intent(this, com.khanakirana.admin.khanakirana.activities.KKMapPolygonSelectionActivity.class);
                Bundle b = new Bundle();
                b.putDouble(KKConstants.LATITUDE_TO_CENTER_ON, 12.9719400);
                b.putDouble(KKConstants.LONGITUDE_TO_CENTER_ON, 77.5936900);
                intent.putExtras(b);
                startActivityForResult(intent, KKAndroidConstants.MAP_POLYGON_FOR_BUSINESS);
                break;*/
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
        super.onActivityResult(requestCode, resultCode, data);
        if (FacebookReadInterface.isInitialied()) {

            FacebookReadInterface.getInstance().processActivityResult(requestCode, resultCode, data);
        }
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
        FacebookReadInterface.fini();
    }
}