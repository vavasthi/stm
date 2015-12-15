package com.avasthi.roadcompanion.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RCTollActivity;
import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;
import com.avasthi.roadcompanion.utils.Constants;
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
        showVehicleStoppedNotification();
    }

    private void showNotification() {

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notification_service_running))
                .setContentText(getResources().getString(R.string.notification_service_running))
                .setSmallIcon(R.drawable.ic_launcher);
        Intent resultIntent = new Intent(this, RoadCompanionMainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(RoadCompanionMainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(R.string.notification_service_running, builder.build());
    }
    private void showVehicleStoppedNotification() {

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.vehicle_stopped_title))
                .setContentText(getResources().getString(R.string.vehicle_stopped_text))
                .setSmallIcon(R.drawable.ic_launcher);
        Intent resultIntent = new Intent(this, RCTollActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(RoadCompanionMainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        Intent tollIntent = new Intent();
        tollIntent.setAction(Constants.TOLL_ACTION_FROM_SERVICE);
        PendingIntent tollPendingIntent = PendingIntent.getBroadcast(this, 0, tollIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_launcher, "Toll", tollPendingIntent);
        builder.setStyle(new Notification.BigTextStyle().bigText(getResources().getString(R.string.vehicle_stopped_text)));
        notificationManager.notify(R.string.notification_service_running, builder.build());
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