package com.android.base.notifications;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.android.base.R;
import com.android.base.utils.NotificationUtil;


/**
 * Created by gagandeep on 12/2/16.
 */
public class NotificationPresets {

    public static final NotificationPreset BASIC = new BasicNotificationPreset();
    public static final NotificationPreset INBOX = new InboxNotificationPreset();
    public static final NotificationPreset BIG_PICTURE = new BigPictureNotificationPreset();

    private static NotificationCompat.Builder applyBasicOptions(Context context,
                                                                NotificationCompat.Builder builder, NotificationCompat.WearableExtender wearableOptions,
                                                                NotificationPreset.BuildOptions options) {
        builder.setContentTitle(options.titlePreset)
                .setContentText(options.textPreset)
                .setSmallIcon(R.mipmap.ic_launcher)
                /*.setDeleteIntent(NotificationUtil.getExamplePendingIntent(
                        context, R.string.example_notification_deleted))*/;
        options.actionsPreset.apply(context, builder, wearableOptions);
        options.priorityPreset.apply(builder, wearableOptions);
        if (options.includeLargeIcon) {
            builder.setLargeIcon(BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.ic_sample_icon));
        }
        if (options.isLocalOnly) {
            builder.setLocalOnly(true);
        }
        if (options.pendingIntent != null) {
            builder.setContentIntent(options.pendingIntent);
        }
        if (options.vibrate) {
            builder.setVibrate(new long[] {0, 100, 50, 100} );
        }
        return builder;
    }

    private static class BasicNotificationPreset extends NotificationPreset {

        public BasicNotificationPreset() {
            super(R.string.basic_example_title, R.string.basic_example_text);
        }

        @Override
        public Notification[] buildNotifications(Context context, BuildOptions options) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            NotificationCompat.WearableExtender wearableOptions = new NotificationCompat.WearableExtender();

            applyBasicOptions(context,builder,wearableOptions,options);

            builder.extend(wearableOptions);
            return new Notification[] {builder.build()};
        }
    }

    private static class InboxNotificationPreset extends NotificationPreset {

        public InboxNotificationPreset() {
            super(R.string.basic_example_title, R.string.basic_example_text);
        }

        @Override
        public Notification[] buildNotifications(Context context, BuildOptions options) {

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine("Inbox style example line 1");
            inboxStyle.addLine("Inbox style example line 2");
            inboxStyle.addLine("Inbox style example line 3");
            inboxStyle.addLine("Inbox style example line 4");
            inboxStyle.addLine("Inbox style example line 5");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setStyle(inboxStyle);
            NotificationCompat.WearableExtender wearableOptions = new NotificationCompat.WearableExtender();

            applyBasicOptions(context,builder,wearableOptions,options);

            builder.extend(wearableOptions);
            return new Notification[] {builder.build()};
        }
    }

    private static class BigPictureNotificationPreset extends NotificationPreset {

        public BigPictureNotificationPreset(){
            super(R.string.basic_example_title, R.string.basic_example_text);
        }

        @Override
        public Notification[] buildNotifications(Context context, BuildOptions options) {

            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_sample_icon));
            
            bigPictureStyle.setBigContentTitle(context.getString(R.string.big_picture_style_example_title));
            bigPictureStyle.setSummaryText(context.getString(R.string.big_picture_style_example_summary));

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setStyle(bigPictureStyle);
            NotificationCompat.WearableExtender wearableOptions = new NotificationCompat.WearableExtender();

            applyBasicOptions(context,builder,wearableOptions,options);

            builder.extend(wearableOptions);
            return new Notification[] {builder.build()};
        }
    }
}
