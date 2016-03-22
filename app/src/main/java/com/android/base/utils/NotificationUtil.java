package com.android.base.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.android.base.notifications.NotificationIntentReceiver;


/**
 * Created by gagandeep on 12/2/16.
 */
public class NotificationUtil {

    public static final String EXTRA_MESSAGE =
            "com.android.base.notifications.MESSAGE";
    public static final String EXTRA_REPLY =
            "com.android.base.notifications.REPLY";

    public static PendingIntent getSamplePendingIntent(Context context,int messageResId) {
        Intent intent = new Intent(NotificationIntentReceiver.ACTION_EXAMPLE).setClass(context,NotificationIntentReceiver.class);
        intent.putExtra(EXTRA_MESSAGE,context.getString(messageResId));
        return PendingIntent.getBroadcast(context, messageResId /* requestCode */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
