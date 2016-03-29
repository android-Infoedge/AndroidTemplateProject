package com.android.base.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;

/**
 * Created by gagandeep on 21/3/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    @Named("ScreenName")
    String mScreenName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActivityComponent();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected abstract void setUpActivityComponent();

    public String getScreenName() {
        return mScreenName;
    }
}
