package com.avasthi.roadcompanion.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.AccountPicker;
import com.avasthi.roadcompanion.KKAndroidConstants;
import com.khanakirana.admin.khanakirana.R;
import com.avasthi.roadcompanion.background.tasks.AuthenticateUserAsyncTask;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.common.KKConstants;

import java.util.Calendar;
import java.util.logging.Logger;

public class RoadCompanionMainActivity extends RoadCompanionAbstractActivity {

    private Logger logger = Logger.getLogger(RoadCompanionMainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedPreferences();
        updateRegistrationLocation();
        telephonyManager = (TelephonyManager)(this.getSystemService(TELEPHONY_SERVICE));
        detectedPhoneNumber = telephonyManager.getLine1Number();
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
            case R.id.add_item:
                startActivityForResult(new Intent(this, KKAddProductInMasterListActivity.class), KKAndroidConstants.CAMERA_REQUEST);
                break;
            case R.id.adding_measurement_category_popup_item:
                startActivityForResult(new Intent(this, KKAddMeasurementCategoryActivity.class), KKAndroidConstants.ADD_MEASUREMENT_CATEGORY_REQUEST);
                break;
            case R.id.adding_measurement_unit_popup_item:
                startActivityForResult(new Intent(this, KKAddMeasurementUnitActivity.class), KKAndroidConstants.ADD_MEASUREMENT_UNIT_REQUEST);
                break;
            case R.id.add_item_category:
                startActivityForResult(new Intent(this, KKManageItemCategoryActivity.class), KKAndroidConstants.ADD_ITEM_CATEGORY_REQUEST);
                break;
            case R.id.kk_set_business_polygon:
                Intent intent = new Intent(this, KKMapPolygonSelectionActivity.class);
                Bundle b = new Bundle();
                b.putDouble(KKConstants.LATITUDE_TO_CENTER_ON, 12.9719400);
                b.putDouble(KKConstants.LONGITUDE_TO_CENTER_ON, 77.5936900);
                intent.putExtras(b);
                startActivityForResult(intent, KKAndroidConstants.MAP_POLYGON_FOR_BUSINESS);
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
        switch (requestCode) {
            case KKAndroidConstants.REQUEST_ACCOUNT_PICKER :
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setSelectedAccountName(accountName);
                        isGoogleAuthentication = Boolean.TRUE;
                        // User is selected, we can splash registration screen now.
                        new AuthenticateUserAsyncTask(this).execute();
                    }
                }
                break;
        }
    }

    private void updateRegistrationLocation() {

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
