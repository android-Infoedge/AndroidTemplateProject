package com.android.base.notifications;

import android.support.v4.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagandeep on 12/2/16.
 */
public class PriorityPresets {

    public static final PriorityPreset DEFAULT = new SimplePriorityPreset(NotificationCompat.PRIORITY_DEFAULT);

    public static final String PRIORITY_MIN = "PRIORITY_MIN";
    public static final String PRIORITY_LOW = "PRIORITY_LOW";
    public static final String PRIORITY_DEFAULT = "DEFAULT";
    public static final String PRIORITY_HIGH = "PRIORITY_HIGH";
    public static final String PRIORITY_MAX = "PRIORITY_MAX";

    public static final Map<String,PriorityPreset> PRESETS = new HashMap<>(5);

    static {
        PRESETS.put(PRIORITY_MIN,new SimplePriorityPreset(NotificationCompat.PRIORITY_MIN));
        PRESETS.put(PRIORITY_LOW,new SimplePriorityPreset(NotificationCompat.PRIORITY_LOW));
        PRESETS.put(PRIORITY_DEFAULT,DEFAULT);
        PRESETS.put(PRIORITY_HIGH,new SimplePriorityPreset(NotificationCompat.PRIORITY_HIGH));
        PRESETS.put(PRIORITY_MAX,new SimplePriorityPreset(NotificationCompat.PRIORITY_MAX));
    }


    private static class SimplePriorityPreset extends PriorityPreset {

        private final int mPriority;

        public SimplePriorityPreset(int mPriority) {
            this.mPriority = mPriority;
        }

        @Override
        public void apply(NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions) {
            builder.setPriority(mPriority);
        }
    }
}
