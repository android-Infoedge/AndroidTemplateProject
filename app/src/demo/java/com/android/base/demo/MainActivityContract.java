package com.android.base.demo;

import android.app.Activity;

import com.android.base.common.mvp.IView;
import com.android.base.common.mvp.Presenter;
import com.android.base.demo.network.models.StackOverflowQuestions;

/**
 * Created by gagandeep on 21/3/16.
 */
public interface MainActivityContract {

    interface View extends IView {
        void onGcmRegistrationClickResult();
        void onAnalyticsEventTestClickResult();
        void onRetrofitCallDemoResultSuccess(StackOverflowQuestions stackOverflowQuestions);
        void onRetrofitCallDemoResultFailure();
    }


    interface MainActivityPresenter extends Presenter<View> {
        void handleGcmRegistrtaionClick(Activity activity);
        void handleAnalyticsEventTestClick();
        void handleRetrofitCallDemoClick();
        void sendScreenViewEvent(String screenName);
    }
}
