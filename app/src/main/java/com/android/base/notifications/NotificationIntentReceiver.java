package com.android.base.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.base.utils.NotificationUtil;


/**
 * Created by gagandeep on 12/2/16.
 */
public class NotificationIntentReceiver extends BroadcastReceiver {


    public static final String ACTION_EXAMPLE =
            "com.android.weatherdekho.notifications.ACTION_EXAMPLE";


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_EXAMPLE)) {
            String message = intent.getStringExtra(NotificationUtil.EXTRA_MESSAGE);
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }
}
