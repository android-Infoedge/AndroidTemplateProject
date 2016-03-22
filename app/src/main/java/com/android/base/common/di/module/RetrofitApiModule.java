package com.android.base.common.di.module;


import com.android.base.network.CommonHeadersInterceptor;
import com.android.base.network.RetrofitLoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by gagandeep on 12/1/16.
 */
@Module
public class RetrofitApiModule {

    private String BASE_URL = "";

    public RetrofitApiModule(String baseUrl) {
        this.BASE_URL = baseUrl;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(CommonHeadersInterceptor headersInterceptor,RetrofitLoggingInterceptor
            loggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(headersInterceptor);
        builder.interceptors().add(loggingInterceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory factory) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(factory);
        return builder.client(okHttpClient).build();
    }

    @Provides
    @Singleton
    RetrofitLoggingInterceptor provideLoggingInterceptor() {
        return new RetrofitLoggingInterceptor();
    }

    @Singleton
    @Provides
    CommonHeadersInterceptor provideCommonHeadersInterceptor() {
        return new CommonHeadersInterceptor();
    }

}
