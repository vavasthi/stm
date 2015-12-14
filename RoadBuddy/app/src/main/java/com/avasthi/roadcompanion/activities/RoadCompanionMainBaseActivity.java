package com.avasthi.roadcompanion.activities;

/**
 * Created by vavasthi on 22/11/15.
 */

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.MemberAndVehicles;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.RCVehicleListAdapter;
import com.avasthi.roadcompanion.service.RCDataCollectorService;
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
    protected MemberAndVehicles currentMemberAndVehicles;
    protected int selectedVehiclePosition = -1;
    protected Boolean dataCollectionServiceRunning = false;

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

    public void splashMainScreen(MemberAndVehicles currentMemberAndVehicles) {

        this.currentMemberAndVehicles = currentMemberAndVehicles;
        isLoggedIn = true;
        hideProgressDialog();
        setContentView(R.layout.road_buddy_main);
        final View v = findViewById(R.id.road_buddy_main_view);
        TextView greeting = (TextView) (v.findViewById(R.id.greeting));
        greeting.setText("Hello " + currentMemberAndVehicles.getMember().getName() + "!");
        saveSharedPreferences();
        final ListView listView = (ListView)v.findViewById(R.id.vehicle_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedVehiclePosition = position;
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVehiclePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listView.setAdapter(new RCVehicleListAdapter(this, currentMemberAndVehicles.getVehicles()));
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
        if (selectedVehiclePosition != -1) {
            listView.setSelection(selectedVehiclePosition);
        }
        hideProgressDialog();
        if (currentMemberAndVehicles != null &&
                currentMemberAndVehicles.getDrive() != null &&
                !currentMemberAndVehicles.getDrive().getDone()) {
            startCollectionService();
        }
        setMainScreenActionResource();

    }
    protected  void startCollectionService() {

        startService(new Intent(this, RCDataCollectorService.class));
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        dataCollectionServiceRunning = true;
    }
    protected  void stopCollectionService() {
        stopService(new Intent(this, RCDataCollectorService.class));
        dataCollectionServiceRunning = false;
    }

    protected void setMainScreenActionResource() {

        setMainScreenActionResource(isServiceRunning(RCDataCollectorService.class));
    }
    protected void setMainScreenActionResource(boolean serviceRunning) {

        Button b = (Button)findViewById(R.id.action_drive);
        if (serviceRunning) {

            b.setText(R.string.stop_drive);
        }
        else {
            b.setText(R.string.start_drive);
        }
    }
    public boolean isServiceRunning(Class<?> serviceClass) {

        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo rsi : am.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(rsi.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    abstract public void continueAuthentication();
}
