package com.android.base.notifications;

import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by gagandeep on 12/2/16.
 */
public abstract class ActionsPreset {

    /** Apply the priority to a notification builder */
    public abstract void apply(Context context, NotificationCompat.Builder builder,
                               NotificationCompat.WearableExtender wearableOptions);
}
