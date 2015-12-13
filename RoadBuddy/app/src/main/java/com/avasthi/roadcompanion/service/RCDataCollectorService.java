package com.avasthi.roadcompanion.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.avasthi.roadcompanion.utils.RCSensorManager;

import java.security.Provider;
import java.util.Locale;

/**
 * Created by vavasthi on 8/12/15.
 */
public class RCDataCollectorService extends Service {

    private NotificationManager notificationManager;
    private Intent notificationIntent;
    private RCDataCollectorServiceBinder binder = new RCDataCollectorServiceBinder();

    public class RCDataCollectorServiceBinder extends Binder {
        RCDataCollectorService getService() {
            return RCDataCollectorService.this;
        }
    }
    @Nullable
    @Override
    public Binder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        RCLocationManager.initialize(context, Locale.getDefault());
        RCSensorManager.initialize(context);
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationIntent = new Intent(this, RoadCompanionMainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        showNotification();
    }

    private void showNotification() {

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notification_service_running))
                .setContentText(getResources().getString(R.string.notification_service_running))
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, RoadCompanionMainActivity.class), 0);
        notificationManager.notify(R.string.notification_service_running, notification);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RCLocationManager.getInstance().fini();
        RCSensorManager.getInstance().fini();
    }

}