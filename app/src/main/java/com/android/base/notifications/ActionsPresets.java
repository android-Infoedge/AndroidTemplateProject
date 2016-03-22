package com.android.base.notifications;

import android.content.Context;
import android.support.v4.app.NotificationCompat;


import com.android.base.R;
import com.android.base.utils.NotificationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gagandeep on 12/2/16.
 */
public class ActionsPresets {

    public static final ActionsPreset NO_ACTIONS_PRESET = new NoActionPreset();
    public static final ActionsPreset SINGLE_ACTIONS_PRESET = new SingleActionPreset();

    public static final String NO_ACTIONS = "NO_ACTIONS_PRESET";
    public static final String SINGLE_ACTIONS = "SINGLE_ACTIONS_PRESET";
    public static final String LONG_TITLE_SINGLE_ACTIONS = "LONG_TITLE_SINGLE_ACTIONS_PRESET";
    public static final String DIFFERENT_ACTIONS = "DIFFERENT_ACTIONS_PRESET";

    public static final Map<String,ActionsPreset> PRESETS = new HashMap<>(5);

    static {
        PRESETS.put(NO_ACTIONS,NO_ACTIONS_PRESET);
        PRESETS.put(SINGLE_ACTIONS,SINGLE_ACTIONS_PRESET);
        PRESETS.put(LONG_TITLE_SINGLE_ACTIONS,new LongTitleActionPreset());
        PRESETS.put(DIFFERENT_ACTIONS,new DifferentActionsOnPhoneAndWearablePreset());
    }


    private static class NoActionPreset extends ActionsPreset {
        @Override
        public void apply(Context context, NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions) {
        }
    }

    private static class SingleActionPreset extends ActionsPreset {
        @Override
        public void apply(Context context, NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions) {
            builder.addAction(R.drawable.ic_sample_icon, context.getString(R.string.example_action), NotificationUtil
                    .getSamplePendingIntent(context, R.string.example_action_clicked)).build();
        }
    }

    private static class LongTitleActionPreset extends ActionsPreset {
        @Override
        public void apply(Context context, NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions) {
            builder.addAction(R.drawable.ic_sample_icon, context.getString(R.string.example_action_long_title), NotificationUtil.getSamplePendingIntent(context, R.string.example_action_clicked)).build();
        }
    }

    private static class DifferentActionsOnPhoneAndWearablePreset extends ActionsPreset {

        @Override
        public void apply(Context context, NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions) {
            NotificationCompat.Action phoneAction = new NotificationCompat.Action.Builder(R.drawable.ic_sample_icon,context.getString(R.string.phone_action),NotificationUtil.getSamplePendingIntent(context,R.string.phone_action_clicked)).build();
            builder.addAction(phoneAction);

            NotificationCompat.Action wearableAction = new NotificationCompat.Action.Builder(R.drawable.ic_sample_icon,context.getString(R.string.wearable_action),NotificationUtil.getSamplePendingIntent(context,R.string.wearable_action_clicked)).build();
            wearableOptions.addAction(wearableAction);
        }
    }

}
