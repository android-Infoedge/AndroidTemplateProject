package com.android.base.common.di.components;



import com.android.base.common.di.module.AppModule;
import com.android.base.common.di.module.GooglePlayServicesApisModule;
import com.android.base.common.di.module.RetrofitApiModule;
import com.android.base.demo.MainActivityComponent;
import com.android.base.demo.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gagandeep on 11/1/16.
 */

@Singleton
@Component(modules = {AppModule.class, RetrofitApiModule.class, GooglePlayServicesApisModule.class})
public interface AppComponent {
    /*WeatherDetailsActivityComponent plus(WeatherDetailsActivityModule weatherDetailsActivityModule);
    ChooseCityActivityComponent plus(ChooseCityActivityModule chooseCityActivityModule);*/

    MainActivityComponent plus(MainActivityModule mainActivityModule);
}
