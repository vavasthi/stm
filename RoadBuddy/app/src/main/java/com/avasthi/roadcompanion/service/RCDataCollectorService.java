package com.avasthi.roadcompanion.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.avasthi.roadcompanion.utils.RCSensorManager;

import java.util.Locale;

/**
 * Created by vavasthi on 8/12/15.
 */
public class RCDataCollectorService extends IntentService {

    public RCDataCollectorService() {
        super("RCDataCollectorService");
    }
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        Context context = getApplicationContext();
        RCLocationManager.initialize(context, Locale.getDefault());
        RCSensorManager.initialize(context);
        String dataString = workIntent.getDataString();
    }
}