package com.android.base.gcm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;


import com.android.base.demo.MainActivity;
import com.android.base.R;
import com.android.base.gcm.model.LocalNotificationCommand;
import com.android.base.gcm.model.NotificationCommand;
import com.android.base.notifications.ActionsPreset;
import com.android.base.notifications.ActionsPresets;
import com.android.base.notifications.NotificationPreset;
import com.android.base.notifications.NotificationPresets;
import com.android.base.notifications.PriorityPreset;
import com.android.base.notifications.PriorityPresets;
import com.android.base.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.android.base.utils.LogUtils.LOGD;


/**
 * This class is responsible for handling GCM message and showing local notification
 * in notification tray of android phone.
 * <p>This just the variation of GCM command excutor , All the functionality of handling
 * GCM message to show local notification,and perform some action while tapping on that
 * notification is written here.</p>
 *
 * @author Gagandeep Singh
 * @version 1.0
 * @see GcmCommandExecutor
 * @since 1.0
 */
public class LocalNotificationExecutor implements GcmCommandExecutor {

    private final static String TAG = LogUtils.makeLogTag(LocalNotificationExecutor.class);
    private Context mContext;
    public static Map<Integer, Class> NOTIFICATION_LANDING_SCREEN_MAP;
    private Random mRandomGenerator;

    static {
        NOTIFICATION_LANDING_SCREEN_MAP = new HashMap<>();
        NOTIFICATION_LANDING_SCREEN_MAP.put(0,MainActivity.class);
    }

    public LocalNotificationExecutor(Context context) {
        mContext = context;
        mRandomGenerator = new Random();
    }

    @Override
    public void execute(NotificationCommand command, String responseString) {
        LOGD(TAG, "LocalNotificationExecutor.execute");
        processCommand((LocalNotificationCommand) command);
    }

    private void processCommand(final LocalNotificationCommand command) {
        if (command == null) {
            return;
        }

        final LocalNotificationCommand localCommand = command;
        Intent intent;
        final PendingIntent resultPendingIntent;

        //getting target screen from map
        Class landingScreen = getLandingScreen(localCommand);
        if (landingScreen == null) {
            LOGD(TAG, "Unsupported notification message");
            return;
        }

        if (localCommand.landingPage == 0) {
            intent = new Intent();
        } else {
            intent = new Intent(mContext, landingScreen);
        }
        resultPendingIntent = addIntentData(intent, localCommand);

        final String title = TextUtils.isEmpty(localCommand.title) ? mContext.getString(R.string.app_name) : localCommand.title;
        final String message = TextUtils.isEmpty(localCommand.message) ? "" : localCommand.message;

        //If photo url received in message is "D" then use icon from resources otherwise download from
        //net and display icon
        if (!"D".equalsIgnoreCase(localCommand.iconUrl)) {
           //TODO - Download image from net and call generateNotification(title, message, resultPendingIntent, command, response.getBitmap());
        } else {
            generateNotification(title, message, resultPendingIntent, command, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        }
    }

    private void generateNotification(String title, String message, PendingIntent pendingIntent, LocalNotificationCommand command, Bitmap icon) {
        int notificationId = getNotificationId(command.landingPage);
        LOGD(TAG, "LocalNotificationExecutor.generateNotification");
        NotificationPreset preset = NotificationPresets.BASIC;
        PriorityPreset priorityPreset = PriorityPresets.PRESETS.get(PriorityPresets.PRIORITY_DEFAULT);
        ActionsPreset actionsPreset = ActionsPresets.PRESETS.get(ActionsPresets.NO_ACTIONS);

        NotificationPreset.BuildOptions options = new NotificationPreset.BuildOptions(title,message,pendingIntent,false,
                false,false,null,actionsPreset,priorityPreset);

        Notification[] notifications = preset.buildNotifications(mContext,options);

        for (int i = 0; i < notifications.length; i++) {
            NotificationManagerCompat.from(mContext).notify(i, notifications[i]);
        }

    }

    private PendingIntent addIntentData(Intent intent, LocalNotificationCommand command) {
        PendingIntent resultPendingIntent;
        /*intent.putExtra(AppConstants.INTENT_KEY_FROM_GCM_NOTIFICATION, true);
        intent.putExtra(AppConstants.INTENT_KEY_GCM_NOTIFICATION_ID, command.notificationId);
        intent.putExtra(AppConstants.INTENT_KEY_GCM_NOTIFICATION_TYPE, command.type);*/

        switch (command.type) {

        }
        resultPendingIntent = PendingIntent.getActivity(mContext, mRandomGenerator.nextInt(200),
                intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        return resultPendingIntent;
    }

    private Class getLandingScreen(LocalNotificationCommand command) {
        switch (command.type) {
            case 0:
                return MainActivity.class;
        }
        return MainActivity.class;
    }

    private int getNotificationId(int landingPage) {

        switch (landingPage) {
            case 1:
                int randomNumber = mRandomGenerator.nextInt(200);
                return randomNumber;
        }
        return mRandomGenerator.nextInt(200);
    }
}
