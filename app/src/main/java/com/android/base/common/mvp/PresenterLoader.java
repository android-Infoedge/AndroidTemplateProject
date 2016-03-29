package com.android.base.common.mvp;

import android.content.Context;
import android.support.v4.content.Loader;

import com.android.base.utils.LogUtils;

/**
 * Created by gagandeep on 29/3/16.
 */
public class PresenterLoader<P extends Presenter> extends Loader<P> {

    private static final String TAG = LogUtils.makeLogTag(PresenterLoader.class.getSimpleName());

    private P mPresenter;

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public PresenterLoader(Context context,P presenter) {
        super(context);
        this.mPresenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        deliverResult(mPresenter);
    }

}
