package com.android.base.notifications;

import android.support.v4.app.NotificationCompat;

/**
 * Created by gagandeep on 12/2/16.
 */
public abstract class PriorityPreset {

    public abstract void apply(NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions);
}
