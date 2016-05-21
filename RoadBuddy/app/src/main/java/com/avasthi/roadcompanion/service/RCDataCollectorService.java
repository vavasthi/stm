package com.avasthi.roadcompanion.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.support.annotation.Nullable;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RCAmenityActivity;
import com.avasthi.roadcompanion.activities.RCTollActivity;
import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;
import com.avasthi.roadcompanion.utils.Constants;
import com.avasthi.roadcompanion.utils.RCSensorManager;

/**
 * Created by vavasthi on 8/12/15.
 */
public class RCDataCollectorService extends Service {

    private NotificationManager notificationManager;
    private Intent notificationIntent;
    private RCDataCollectorServiceBinder binder = new RCDataCollectorServiceBinder();

    public class RCDataCollectorServiceBinder extends Binder {
        public RCDataCollectorService getService() {
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
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationIntent = new Intent(this, RoadCompanionMainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        RCSensorManager.initialize(this);
        showNotification();
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
    public void showVehicleStoppedNotification() {

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.vehicle_stopped_title))
                .setContentText(getResources().getString(R.string.vehicle_stopped_text))
                .setSmallIcon(R.drawable.toll)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.toll));
        builder.setStyle(new Notification.BigTextStyle().bigText(getResources().getString(R.string.vehicle_stopped_text)));
        builder.setPriority(Notification.PRIORITY_MAX);
        addAction(builder, RCAmenityActivity.class, R.string.ameneties, R.drawable.restaurant, Constants.RESTAURANT_ACTION_FROM_SERVICE);
        addAction(builder, RCTollActivity.class, R.string.toll, R.drawable.toll, Constants.TOLL_ACTION_FROM_SERVICE);

        Intent resultIntent = new Intent(getApplicationContext(), RCTollActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setFullScreenIntent(resultPendingIntent, true);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(R.string.vehicle_stopped_title, builder.build());
    }
    private void addAction(Notification.Builder builder, Class<?> cls, int label, int drawable, String actionConstant) {

        Intent intent = new Intent(this, cls);
        intent.setAction(actionConstant);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new Notification.Action.Builder(drawable, getResources().getString(label), pendingIntent).build());

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RCSensorManager.getInstance().fini();
    }

    public void cancelVehicleStoppedNotification() {
        notificationManager.cancel(R.string.vehicle_stopped_title);
    }
}