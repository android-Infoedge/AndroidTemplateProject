package com.android.base.utils;

import android.content.Context;

import com.android.base.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import static com.android.base.utils.LogUtils.LOGD;


/**
 * Manages tracking at google analytics.It encapsulates google analytics related code at
 * one place.
 *
 * This class performs following functions :- <br/>
 * <ul>
 *     <li>Initializes {@link Tracker} with appropriate configurations</li>
 *     <li>Tracks screen views</li>
 *     <li>Tracks Events</li>
 * </ul>
 */
public final class GoogleAnalyticsManager {

    private final static String TAG = LogUtils.makeLogTag(GoogleAnalyticsManager.class);

    private Context mContext = null;
    private Tracker mTracker;
    private PrefUtils mPrefUtils;

    public GoogleAnalyticsManager(Context context,Tracker tracker,PrefUtils prefUtils) {
        this.mContext = context;
        this.mTracker = tracker;
        this.mPrefUtils = prefUtils;
    }

    public synchronized void setTracker(Tracker tracker) {
        mTracker = tracker;
    }

    /**
     * Decides if google analytics tracking logic can send views or events.It checks if
     * user specifically disabled google analytics tracking.
     *
     * @return true if tracking can be done otherwise false
     */
    private boolean canSend() {
        // We can only send Google Analytics when ALL the following conditions are true:
        //    1. This module has been initialized.
        //    2. The user has accepted the ToS.
        //    3. Analytics is enabled in Settings.
        return mContext != null && mTracker != null &&
                mPrefUtils.isAnalyticsEnabled();
    }

    /**
     * Sends information to google analytics that this screen has been viewed.Later this information
     * can be used to determine which screen is mostly used by users.
     *
     * @param screenName screen to be tracked
     */
    public void sendScreenView(String screenName) {
        if (canSend()) {
            mTracker.setScreenName(screenName);
            mTracker.send(new HitBuilders.AppViewBuilder().build());
            LOGD(TAG, "Screen View recorded: " + screenName);
        } else {
            LOGD(TAG, "Screen View NOT recorded (analytics disabled or not ready).");
        }
    }

    /**
     * Sends information about some event performed on some entity(eg. feedback button clicked on
     * feedback activity) to google analytics that .Later this information used to track down what
     * features in what screen are moslty used and where(location of user).
     *
     * @param category meaningful category name of event
     * @param action meaningful action name of event
     * @param label meaningful label of event
     * @param value
     */
    public void sendEvent(String category, String action, String label, long value) {
        if (canSend()) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .setValue(value)
                    .build());
            LOGD(TAG, "Event recorded:");
            LOGD(TAG, "\tCategory: " + category);
            LOGD(TAG, "\tAction: " + action);
            LOGD(TAG, "\tLabel: " + label);
            LOGD(TAG, "\tValue: " + value);
        } else {
            LOGD(TAG, "Analytics event ignored (analytics disabled or not ready).");
        }
    }

    public void sendEvent(String category, String action, String label) {
        sendEvent(category, action, label, 0);
    }

    public Tracker getTracker() {
        return mTracker;
    }

    /**
     * Initializes Analytics tracker from xml depending on running mode(dev or prod) of
     * application.
     * @param context {@link Context}
     */
    public synchronized void initializeAnalyticsTracker(Context context) {
        mContext = context;
        if (mTracker == null) {
            mTracker = GoogleAnalytics.getInstance(context).newTracker(R.xml.google_analytics);
        }
    }

    public void sendAppSpeed(String category, String action, String label, long value){
        if (canSend()) {
            mTracker.send(new HitBuilders.TimingBuilder()
                    .setCategory(category)
                    .setValue(value)
                    .setVariable(action)
                    .setLabel(label)
                    .build());
            LOGD(TAG, "Event recorded:");
            LOGD(TAG, "\tCategory: " + category);
            LOGD(TAG, "\tAction: " + action);
            LOGD(TAG, "\tLabel: " + label);
            LOGD(TAG, "\tValue: " + value);
        } else {
            LOGD(TAG, "Analytics event ignored (analytics disabled or not ready).");
        }

    }
}
