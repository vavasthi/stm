package com.avasthi.roadcompanion.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/12/15.
 */
public class CloudMessagingService extends IntentService {
    public CloudMessagingService() {
        super(CloudMessagingService.class.getName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("CloudMsg", "notification received");
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (extras != null && !extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger(CloudMessagingService.class.getName()).log(Level.INFO, extras.toString());
                showToast(extras.getString("message"));
            }
        }
    }
    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }}
