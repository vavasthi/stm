package com.sanjnan.vitarak.app.customer.activities;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sanjnan.vitarak.app.customer.SanjnanAndroidConstants;
import com.khanakirana.khanakirana.R;
import com.sanjnan.vitarak.app.customer.tasks.AuthenticateUserAsyncTask;
import com.sanjnan.vitarak.app.customer.tasks.IsRegisteredUserAsyncTask;
import com.sanjnan.vitarak.app.customer.tasks.RegisterUserAsyncTask;
import com.sanjnan.vitarak.app.customer.utils.EndpointManager;
import com.sanjnan.vitarak.app.customer.utils.KKLocationManager;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class KhanaKiranaMainActivity extends FragmentActivity {

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

    private Logger logger = Logger.getLogger(KhanaKiranaMainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();
        KKLocationManager.initialize(this, getResources().getConfiguration().locale);
        telephonyManager = (TelephonyManager)(this.getSystemService(Context.TELEPHONY_SERVICE));
        detectedPhoneNumber = telephonyManager.getLine1Number();
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
        if (KKLocationManager.getInstance().getLastAddress() != null) {
            ((TextView)findViewById(R.id.detected_city)).setText(KKLocationManager.getInstance().getLastAddress().getLocality());
            ((TextView)findViewById(R.id.detected_state)).setText(KKLocationManager.getInstance().getLastAddress().getAdminArea());
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.kk_user_location_on_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                float zoomLevel = (googleMap.getMaxZoomLevel() - googleMap.getMinZoomLevel()) * 0.60F;
                googleMap.setMyLocationEnabled(Boolean.TRUE);
                MarkerOptions mo = new MarkerOptions();
                Location ll = KKLocationManager.getInstance().getLastLocation();
                if (ll != null) {

                    LatLng latLng = new LatLng(ll.getLatitude(), ll.getLongitude());
                    mo.position(latLng).title(getResources().getString(R.string.kk_you_are_here));
                    googleMap.addMarker(mo);
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, zoomLevel)));
                }
                UiSettings uis = googleMap.getUiSettings();
                uis.setCompassEnabled(Boolean.TRUE);
                uis.setZoomControlsEnabled(Boolean.TRUE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SanjnanAndroidConstants.REQUEST_ACCOUNT_PICKER :
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

    public static String getDetectedPhoneNumber() {

        if (detectedPhoneNumber == null) {
            detectedPhoneNumber = telephonyManager.getLine1Number();
        }
        return detectedPhoneNumber;
    }
    @Override
    protected void onPause() {
        super.onPause();
        KKLocationManager.getInstance().pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        KKLocationManager.getInstance().resume();
    }
}
