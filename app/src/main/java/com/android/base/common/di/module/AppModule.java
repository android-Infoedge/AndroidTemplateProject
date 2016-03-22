package com.android.base.common.di.module;

import android.app.Application;
import android.content.Context;

import com.android.base.utils.GoogleAnalyticsManager;
import com.android.base.utils.PrefUtils;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by gagandeep on 11/1/16.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    public PrefUtils providePrefUtils(Context context) {
        return new PrefUtils(context);
    }


}
