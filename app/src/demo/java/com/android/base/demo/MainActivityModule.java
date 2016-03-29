package com.android.base.demo;

import com.android.base.common.di.ActivityScope;
import com.android.base.common.mvp.Presenter;
import com.android.base.demo.network.api.StackOverflowAPI;
import com.android.base.gcm.GcmRegistrationManager;
import com.android.base.utils.GoogleAnalyticsManager;
import com.android.base.utils.LogUtils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by gagandeep on 21/3/16.
 */
@Module
public class MainActivityModule {

    private static final String TAG = LogUtils.makeLogTag(MainActivityModule.class.getSimpleName());

    private MainActivityContract.View view;
    private String screenName;

    public MainActivityModule(MainActivityContract.View view,String screenName) {
        this.screenName = screenName;
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainActivityContract.MainActivityPresenter provideMainActivityPresenter(GcmRegistrationManager
                                                                                   gcmRegistrationManager,
                                                                GoogleAnalyticsManager analyticsManager,
                                                                StackOverflowAPI stackOverflowAPI) {
        LogUtils.LOGD(TAG, "Creating MainActivityPresenter");
        return new MainActivityPresenter(this.view, gcmRegistrationManager,analyticsManager,stackOverflowAPI);
    }



    @Named("ScreenName")
    @ActivityScope
    @Provides
    String provideScreenName() {
        return screenName;
    }

    @Provides
    @ActivityScope
    StackOverflowAPI provideStackOverflowAPI(Retrofit retrofit) {
        return retrofit.create(StackOverflowAPI.class);
    }


}
