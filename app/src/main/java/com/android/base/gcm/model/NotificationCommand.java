package com.android.base.gcm.model;

import com.google.gson.annotations.SerializedName;

public class NotificationCommand {
    @SerializedName("notificationType")
    public int type;

    public String notificationId;

}
