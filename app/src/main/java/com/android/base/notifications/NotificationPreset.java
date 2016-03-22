package com.android.base.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

/**
 * Created by gagandeep on 12/2/16.
 */
public abstract class NotificationPreset {

    public final int titleResId;
    public final int textResId;

    public NotificationPreset(int titleResId, int textResId) {
        this.titleResId = titleResId;
        this.textResId = textResId;
    }

    public static class BuildOptions {

        public final CharSequence titlePreset;
        public final CharSequence textPreset;
        public final boolean includeLargeIcon;
        public final boolean isLocalOnly;
        public final boolean vibrate;
        public final Integer[] backgroundIds;
        public ActionsPreset actionsPreset;
        public PriorityPreset priorityPreset;
        public PendingIntent pendingIntent;

        public BuildOptions(CharSequence titlePreset, CharSequence textPreset,PendingIntent pendingIntent, boolean
                includeLargeIcon, boolean
                isLocalOnly, boolean vibrate, Integer[] backgroundIds, ActionsPreset actionsPreset, PriorityPreset priorityPreset) {
            this.titlePreset = titlePreset;
            this.textPreset = textPreset;
            this.includeLargeIcon = includeLargeIcon;
            this.isLocalOnly = isLocalOnly;
            this.vibrate = vibrate;
            this.backgroundIds = backgroundIds;
            this.actionsPreset = actionsPreset;
            this.priorityPreset = priorityPreset;
            this.pendingIntent = pendingIntent;
        }
    }

    public abstract Notification[] buildNotifications(Context context,BuildOptions options);

    /** Whether actions are required to use this preset. */
    public boolean actionsRequired() {
        return false;
    }
}
