package com.android.base;

import android.app.Application;
import android.content.Context;

import com.android.base.common.di.components.AppComponent;
import com.android.base.common.di.components.DaggerAppComponent;
import com.android.base.common.di.module.AppModule;
import com.android.base.common.di.module.RetrofitApiModule;
import com.android.base.utils.UrlConfig;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by gagandeep on 18/3/16.
 */
public class AppController extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initAppComponent();
    }

    private void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .retrofitApiModule(new RetrofitApiModule(UrlConfig.URL_BASE)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }
}
