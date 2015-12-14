package com.avasthi.roadcompanion.receivers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.avasthi.roadcompanion.service.CloudMessagingService;

/**
 * Created by vavasthi on 14/12/15.
 */
public class CloudMessagingBroadcastReceiver extends WakefulBroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName cn = new ComponentName(context.getPackageName(), CloudMessagingService.class.getName());
        startWakefulService(context, intent.setComponent(cn));
        setResultCode(Activity.RESULT_OK);
        Log.i(CloudMessagingBroadcastReceiver.class.getName(), "notification received");
    }
}
