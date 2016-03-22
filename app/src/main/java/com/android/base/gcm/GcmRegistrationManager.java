package com.android.base.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;


import com.android.base.utils.AppConstants;
import com.android.base.utils.AppUtils;
import com.android.base.utils.GoogleAnalyticsManager;
import com.android.base.utils.LogUtils;
import com.android.base.utils.PrefUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.android.base.utils.LogUtils.LOGD;


/**
 * Manages or Handles GCM registration process end to end for application.
 * All GCM registration related code is written in this class.
 * <p/>
 * Mainly handles following functions :-
 * <ul>
 * <li>Before registering application for GCM it checks if android device has
 * installed google play services.If not installed then gracefully handle display
 * appropriate condition ,show dialogs guiding user to install play services.
 * <li>Check if device is already registered with GCM server and has stored token
 * in shared preferences.
 * <li>If device has not registered already then GCM registration is initiated.
 * <li>On successful registration, stores token in shared preferences.
 * <li>After saving in shared preferences , token is sent to application server.
 * <li>Also handles flag if GCM token is sent to application server.
 * <li>Refreshes GCM token when google server send message to application that token
 * has expired and application needs to get new one.
 * </ul>
 *
 * @author Gagandeep Singh
 * @author Gagandeep Singh
 * @version 1.0
 * @since 1.0
 */

public class GcmRegistrationManager {

    private final static String TAG = LogUtils.makeLogTag(GcmRegistrationManager.class);

    private Context mContext;
    private GoogleAnalyticsManager mGoogleAnalyticsManager;
    private PrefUtils mPrefUtils;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    //GCM console app id
    private final String SENDER_ID = "1091115172074";

    private GoogleCloudMessaging mGcm;
    private static GcmRegistrationManager mInstance;
    private String mRegistrationId;


    public GcmRegistrationManager(Context context,GoogleAnalyticsManager analyticsManager,PrefUtils prefUtils) {
        this.mContext = context;
        this.mGoogleAnalyticsManager = analyticsManager;
        this.mPrefUtils = prefUtils;
    }


    public void init() {
        /*GcmRegistrationManager gcmRegistrationManager = new GcmRegistrationManager(getApplicationContext());
        if (gcmRegistrationManager.checkPlayServices(this)) {
            gcmRegistrationManager.init();
        }*/
        mGcm = GoogleCloudMessaging.getInstance(mContext);
        mRegistrationId = getRegistrationId();
        LOGD(TAG, "Registration id from local :: " + mRegistrationId);
        if (mRegistrationId.isEmpty()) {
            LOGD(TAG, "Sending Registration id to server ");
            registerInBackground();
        } else {
            boolean isSent = mPrefUtils.isGcmIdSentToServer();
            if (!isSent) {
                LOGD(TAG, "Registration id was not sent to backend,sending it now");
                sendRegistrationIdToBackend();
            }
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(activity, resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId() {
        String registrationId = mPrefUtils.getRegisteredGcmId();
        if (registrationId.isEmpty()) {
            LOGD(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = mPrefUtils.getAppVersion();
        int currentVersion = AppUtils.getAppVersion(mContext);
        if (registeredVersion != currentVersion) {
            LOGD(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }


    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    /*if (mGcm == null) {
                        mGcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    mRegistrationId = mGcm.register(SENDER_ID);
                    LOGD(TAG, "Length -> " + mRegistrationId.length());*/
                    InstanceID instanceID = InstanceID.getInstance(mContext);
                    mRegistrationId = instanceID.getToken(SENDER_ID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                    msg = "Device registered, registration ID=" + mRegistrationId;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    mPrefUtils.setRegisteredGcmId(mRegistrationId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            protected void onPostExecute(String msg) {
                //mDisplay.append(msg + "\n");
                LOGD(TAG, msg);
            }
        }.execute(null, null, null);
    }

    /**
     * Sends GCM registration id obtained from google server to application
     * server.
     * <p>
     * When android application registers or refreshes GCM id from android
     * server then it needs to be sent to application  server so that application
     * can receive GCM messages.</p>
     */
    private void sendRegistrationIdToBackend() {

    }
}
