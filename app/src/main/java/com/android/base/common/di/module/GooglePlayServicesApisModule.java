package com.android.base.common.di.module;

import android.content.Context;

import com.android.base.R;
import com.android.base.gcm.GcmRegistrationManager;
import com.android.base.utils.GoogleAnalyticsManager;
import com.android.base.utils.PrefUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gagandeep on 21/3/16.
 */
@Module
public class GooglePlayServicesApisModule {

    @Provides
    @Singleton
    public Tracker provideGoogleAnalyticsTracker(Context context) {
        return GoogleAnalytics.getInstance(context).newTracker(R.xml.google_analytics);
    }


    @Provides
    @Singleton
    public GoogleAnalyticsManager provideGoogleAnalyticsManager(Context context, Tracker tracker, PrefUtils prefUtils) {
        return new GoogleAnalyticsManager(context, tracker, prefUtils);
    }


    @Provides
    @Singleton
    public GcmRegistrationManager provideGcmRegistrationManager(Context context,GoogleAnalyticsManager
            googleAnalyticsManager,PrefUtils prefUtils) {
        return new GcmRegistrationManager(context,googleAnalyticsManager,prefUtils);
    }
}
