package com.android.base.utils;

import com.android.base.BuildConfig;

/**
 * Created by gagandeep on 21/3/16.
 */
public final class UrlConfig {

    private UrlConfig() {}

    public static final String URL_BASE = BuildConfig.DEBUG?"https://api.stackexchange.com":"https://api.stackexchange.com";


}
