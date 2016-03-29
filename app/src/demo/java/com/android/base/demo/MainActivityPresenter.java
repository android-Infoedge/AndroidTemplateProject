package com.android.base.demo;

import android.app.Activity;

import com.android.base.demo.network.api.StackOverflowAPI;
import com.android.base.demo.network.models.StackOverflowQuestions;
import com.android.base.gcm.GcmRegistrationManager;
import com.android.base.utils.GoogleAnalyticsManager;
import com.android.base.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gagandeep on 21/3/16.
 */
public class MainActivityPresenter implements MainActivityContract.MainActivityPresenter {

    private final static String TAG = LogUtils.makeLogTag(MainActivityPresenter.class);

    private GcmRegistrationManager gcmRegistrationManager;
    private MainActivityContract.View mView;
    private GoogleAnalyticsManager mGoogleAnalyticsManager;
    private StackOverflowAPI mStackOverflowAPI;

    public MainActivityPresenter(MainActivityContract.View view,GcmRegistrationManager gcmRegistrationManager,
                                 GoogleAnalyticsManager analyticsManager,StackOverflowAPI stackOverflowAPI) {
        this.mView = view;
        this.gcmRegistrationManager = gcmRegistrationManager;
        this.mGoogleAnalyticsManager = analyticsManager;
        this.mStackOverflowAPI = stackOverflowAPI;
    }

    @Override
    public void handleGcmRegistrtaionClick(Activity activity) {
        if (gcmRegistrationManager.checkPlayServices(activity)) {
            gcmRegistrationManager.init();
        }

        mView.onGcmRegistrationClickResult();
    }

    @Override
    public void handleAnalyticsEventTestClick() {
        mView.onAnalyticsEventTestClickResult();
    }

    @Override
    public void handleRetrofitCallDemoClick() {
        Call<StackOverflowQuestions> call = mStackOverflowAPI.loadQuestions("android");
        call.enqueue(new Callback<StackOverflowQuestions>() {
            @Override
            public void onResponse(Response<StackOverflowQuestions> response) {
                LogUtils.LOGD(TAG,response.body().toString());
                mView.onRetrofitCallDemoResultSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtils.LOGD(TAG,t.getMessage());
                mView.onRetrofitCallDemoResultFailure();
            }
        });
    }

    @Override
    public void sendScreenViewEvent(String screenName) {
        mGoogleAnalyticsManager.sendScreenView(screenName);
    }

    @Override
    public void onViewAttached(MainActivityContract.View view) {

    }

    @Override
    public void onViewDetached() {

    }

    @Override
    public void onDestroyed() {

    }
}
