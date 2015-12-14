package com.avasthi.roadcompanion.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.avasthi.roadcompanion.activities.RCTollActivity;
import com.avasthi.roadcompanion.utils.Constants;

/**
 * Created by vavasthi on 14/12/15.
 */
public class ServicePendingIntentBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constants.TOLL_ACTION_FROM_SERVICE.equals(intent.getAction())) {
            context.startActivity(new Intent(context, RCTollActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
