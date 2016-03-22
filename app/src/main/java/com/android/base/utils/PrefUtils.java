package com.android.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by gagandeep on 18/3/16.
 */
public final class PrefUtils {

    private SharedPreferences mSharedPreferences;

    public PrefUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final String PREF_ANALYTICS_ENABLED = "pref_analytics_enabled";
    public static final String PREF_GCM_REGISTRATION_ID = "pref_gcm_registration_id";
    public static final String PREF_GCM_ID_SENT_TO_SERVER = "pref_gcm_id_sent_to_server";
    public static final String PREF_APP_VERSION = "pref_app_version";

    public boolean isAnalyticsEnabled() {
        return mSharedPreferences.getBoolean(PREF_ANALYTICS_ENABLED, true);
    }

    public void setRegisteredGcmId(String gcmId) {
        putString(PREF_GCM_REGISTRATION_ID,gcmId);
    }

    public String getRegisteredGcmId() {
        return mSharedPreferences.getString(PREF_GCM_REGISTRATION_ID,"");
    }

    public void setGcmIdSentToServer(boolean isSent) {
        putBoolean(PREF_GCM_ID_SENT_TO_SERVER,isSent);
    }

    public boolean isGcmIdSentToServer() {
        return mSharedPreferences.getBoolean(PREF_GCM_ID_SENT_TO_SERVER, true);
    }

    public int getAppVersion() {
        return mSharedPreferences.getInt(PREF_APP_VERSION,Integer.MIN_VALUE);
    }

    public void setAppVersion(int version) {
        putInt(PREF_APP_VERSION,version);
    }

    private void putBoolean(String key,boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        editor.apply();
    }

    private void putString(String key,String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        editor.apply();
    }

    private void putInt(String key,int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        editor.apply();
    }

}
