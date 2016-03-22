package com.android.base.gcm;

import android.os.Bundle;

import com.android.base.gcm.model.LocalNotificationCommand;
import com.android.base.gcm.model.NotificationCommand;
import com.android.base.utils.LogUtils;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import static com.android.base.utils.LogUtils.LOGD;
import static com.android.base.utils.LogUtils.LOGE;
import static com.android.base.utils.LogUtils.LOGI;

/**
 * Created by gagandeep on 18/3/16.
 */
public class GcmHandlerService extends GcmListenerService {

    private final static String TAG = LogUtils.makeLogTag(GcmHandlerService.class);

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        LOGD(TAG, "GCMListenerService.onMessageReceived :: " + from + " :: " + data.toString());
        String extraData = data.getString("extraData");
        NotificationCommand command = parseGcmData(extraData, NotificationCommand.class);
        command = parseGcmData(extraData, getNotificationParsingClass(command.type));

        GcmCommandExecutor executor = chooseCommandExecutor(command.type);
        executor.execute(command,extraData);
    }

    private GcmCommandExecutor chooseCommandExecutor(int type) {
        switch (type) {
            case 0:
                return new LocalNotificationExecutor(getApplicationContext());
        }
        return new LocalNotificationExecutor(getApplicationContext());
    }

    private Class<? extends NotificationCommand> getNotificationParsingClass(int type) {
        switch (type) {
            case 0:
                return LocalNotificationCommand.class;
        }
        return NotificationCommand.class;
    }

    private NotificationCommand parseGcmData(String extraData, Class<? extends NotificationCommand>
            notificationCommandClass) {
        LOGI(TAG, "Parsing GCM notification command: " + extraData);
        Gson gson = new Gson();
        NotificationCommand command;
        try {
            command = gson.fromJson(extraData, notificationCommandClass);
            if (command == null) {
                LOGE(TAG, "Failed to parse command (gson returned null).");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGE(TAG, "Failed to parse GCM notification command.");
            return null;
        }
        return command;
    }
}
