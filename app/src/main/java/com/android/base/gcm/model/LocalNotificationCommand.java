package com.android.base.gcm.model;

import com.android.base.utils.AppConstants;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gagandeep on 18/3/16.
 */
public class LocalNotificationCommand extends NotificationCommand {

    public int landingPage = 0;
    public String iconUrl;
    public String title;
    public String message;
}
