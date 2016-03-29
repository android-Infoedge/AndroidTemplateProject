package com.android.base;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.android.base.common.BaseActivity;
import com.android.base.common.mvp.IView;
import com.android.base.common.mvp.Presenter;
import com.android.base.common.mvp.PresenterLoader;
import com.android.base.demo.MainActivityContract;
import com.android.base.utils.LogUtils;

/**
 * Created by gagandeep on 29/3/16.
 */
public abstract class PresenterLoadableBaseActivity extends BaseActivity implements LoaderManager
        .LoaderCallbacks<Presenter> {

    private final static String TAG = LogUtils.makeLogTag(PresenterLoadableBaseActivity.class);

    private static final int ID_PRESENTER_LOADER = 1;

    private Presenter<? extends IView> mPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        Loader<Presenter> presenterLoader = getSupportLoaderManager().initLoader(ID_PRESENTER_LOADER, null,
                this);
    }

    protected Presenter<? extends IView> getPresenter() {
        return mPresenter;
    }

    @Override
    public Loader<Presenter> onCreateLoader(int id, Bundle args) {
       return new PresenterLoader<>(getApplicationContext(),provideUiPresenter());
    }

    @Override
    public void onLoadFinished(Loader<Presenter> loader, Presenter presenter) {
        if(presenter == null) {
            LogUtils.LOGD(TAG,"onLoadFinished, presenter came null so loading again = "+presenter);
            getSupportLoaderManager().restartLoader(ID_PRESENTER_LOADER,null,this);
            return;
        }
        LogUtils.LOGD(TAG,"onLoadFinished = "+presenter);
        mPresenter = presenter;
        onPresenterLoaded();
    }

    @Override
    public void onLoaderReset(Loader<Presenter> loader) {

    }

    protected abstract Presenter provideUiPresenter();

    protected void onPresenterLoaded() {
    }
}
