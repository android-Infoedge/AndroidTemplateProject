package com.android.base.demo;

import android.os.Bundle;
import android.widget.Toast;

import com.android.base.AppController;
import com.android.base.R;
import com.android.base.common.di.BaseActivity;
import com.android.base.demo.network.models.Question;
import com.android.base.demo.network.models.StackOverflowQuestions;
import com.android.base.utils.LogUtils;

import javax.inject.Inject;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private final static String TAG = LogUtils.makeLogTag(MainActivity.class);

    @Inject
    MainActivityContract.UserActionsListener mUserActionsListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserActionsListener.sendScreenViewEvent(getScreenName());
    }

    @Override
    protected void setUpActivityComponent() {
        AppController.get(getApplicationContext()).getAppComponent().plus(new MainActivityModule(this,"Main Activity"))
                .inject(this);
        if(mUserActionsListener == null) {
            throw new IllegalStateException("Presenter for this view cannot be null");
        }

        Toast.makeText(MainActivity.this, getScreenName(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_gcm_register_request)
    public void onGcmRegistrationButtonClicked() {
        mUserActionsListener.handleGcmRegistrtaionClick(this);
    }

    @OnClick(R.id.bt_analytics_event_test)
    public void onAnalyticsEventButtonClicked() {
        mUserActionsListener.handleAnalyticsEventTestClick();
    }

    @OnClick(R.id.bt_retrofit_call_demo)
    public void onRetrofitCallDemoButtonClicked() {
        mUserActionsListener.handleRetrofitCallDemoClick();
    }

    @Override
    public void onGcmRegistrationClickResult() {
        Toast.makeText(MainActivity.this, "Gcm Registration handled from presenter", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnalyticsEventTestClickResult() {
        Toast.makeText(MainActivity.this, "Analytics test handled from presenter", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetrofitCallDemoResultSuccess(StackOverflowQuestions stackOverflowQuestions) {
        Toast.makeText(MainActivity.this, stackOverflowQuestions.toString(), Toast.LENGTH_SHORT).show();
        printLoadedQuestions(stackOverflowQuestions);
    }

    @Override
    public void onRetrofitCallDemoResultFailure() {
        Toast.makeText(MainActivity.this, "Retrofit Call Demo Failure", Toast.LENGTH_SHORT).show();
    }

    private void printLoadedQuestions(StackOverflowQuestions stackOverflowQuestions) {
        if(stackOverflowQuestions == null) {
            return;
        }

        for (Question question : stackOverflowQuestions.items) {
            LogUtils.LOGD(TAG,question.toString());
        }
    }

}
