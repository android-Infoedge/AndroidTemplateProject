package com.android.base.demo;

import com.android.base.common.di.ActivityScope;
import com.android.base.common.mvp.Presenter;

import dagger.Subcomponent;

/**
 * Created by gagandeep on 21/3/16.
 */
@ActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);

    MainActivityContract.MainActivityPresenter presenter();
}
