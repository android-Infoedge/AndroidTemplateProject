package com.android.base.common.mvp;

/**
 * Created by gagandeep on 29/3/16.
 */
public interface Presenter<V extends IView> {
    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();
}
