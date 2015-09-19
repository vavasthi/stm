package com.avasthi.android.apps.roadbuddy;


import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.appspot.myapplicationid.roadMeasurementBeanApi.RoadMeasurementBeanApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoadConditionFullscreenActivity extends Activity implements Application.ActivityLifecycleCallbacks {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    SensorReading rotationVector;
    SensorReading linearAcceleration;


    private SharedPreferences settings;
    private String accountName;
    private GoogleAccountCredential credential;
    private RoadMeasurementBeanApi api;
    private DatabaseHandler dbHandler = new DatabaseHandler(this);
    protected static final int REQUEST_CODE_RESOLUTION = 1;
    private static final int REQUEST_AUTHORIZATION = 1;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private GoogleApiClient apiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_condition_fullscreen);



        final View controlsView = findViewById(R.id.top_content_frame);
        final View contentView = findViewById(R.id.bottom_content_controls);
        dbHandler = new DatabaseHandler(this);

        RoadMeasurementBeanApi.Builder builder = new RoadMeasurementBeanApi.Builder(
                AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
                null/*activity.getCredential()*/)
                .setApplicationName("RoadBuddy")
/*                    .setRootUrl("http://localhost:8080/_ah/api/")*/
                .setRootUrl("https://road-condition-data.appspot.com/_ah/api/");
/*                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/

        api = builder.build();

/*        //Account stuff
        settings = getSharedPreferences("com.avasthi.android.apps.roadbuddy", 0);
        credential = GoogleAccountCredential.usingAudience(this,"audience:server:client_id:" + Constants.WEB_CLIENT_ID);
        setAccountName(settings.getString("ACCOUNT_NAME", null));
        if (credential.getSelectedAccountName() != null) {
            // Already signed in, begin app!
            Toast.makeText(getBaseContext(), "Logged in with : " + credential.getSelectedAccountName(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getBaseContext(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext()),Toast.LENGTH_SHORT).show();
        } else {
            // Not signed in, show login window or request an account.
            chooseAccount();
        }*/
        new MeasurementUploadTask().execute(this);
        getApplication().registerActivityLifecycleCallbacks(this);

    }
    // setAccountName definition
    private void setAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ACCOUNT_NAME", accountName);
        editor.commit();
        credential.setSelectedAccountName(accountName);
        this.accountName = accountName;
    }

    void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }

    public GoogleAccountCredential getCredential() {
        return credential;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        SensorInterface.init(this);
        apiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(SensorInterface.getInstance()).addOnConnectionFailedListener(SensorInterface.getInstance()).build();
        apiClient.connect();

    }

    public GoogleApiClient getApiClient() {
        return apiClient;
    }




    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK) {

                    if (data != null && data.getExtras() != null) {
                        String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                        if (accountName != null) {
                            setAccountName(accountName);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("ACCOUNT_NAME", accountName);
                            editor.commit();
                            // User is authorized.
                        }
                    }
                }
                break;
        }
    }

    public RoadMeasurementBeanApi getApi() {
        return api;
    }

    public void setApi(RoadMeasurementBeanApi api) {
        this.api = api;
    }
    public DatabaseHandler getDbHandler() {
        return dbHandler;
    }
}
