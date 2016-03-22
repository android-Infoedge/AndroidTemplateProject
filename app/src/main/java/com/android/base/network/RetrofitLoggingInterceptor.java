package com.android.base.network;

import com.android.base.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.android.base.utils.LogUtils.LOGD;

/**
 * Created by gagandeep on 21/3/16.
 */
public class RetrofitLoggingInterceptor implements Interceptor {

    private final static String TAG = LogUtils.makeLogTag(RetrofitLoggingInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LOGD(TAG, "******************** <<<<<<<<<<  LOGGING REQUEST >>>>>>>>>>  ********************");
        logRequestHeaders(request.headers());
        logRequestMisc(request);
        LOGD(TAG, "xxxxxxxxxxxxxxxxxxxx <<<<<<<<<<  LOGGING REQUEST >>>>>>>>>>  xxxxxxxxxxxxxxxxxxxx\n\n\n\n");
        Response response = chain.proceed(request);
        LOGD(TAG, "******************** <<<<<<<<<<  LOGGING RESPONSE >>>>>>>>>>  ********************");
        logResponseMisc(response);
        LOGD(TAG, "xxxxxxxxxxxxxxxxxxxx <<<<<<<<<<  LOGGING RESPONSE >>>>>>>>>>  xxxxxxxxxxxxxxxxxxxx");
        return response;
    }

    private void logRequestHeaders(Headers headers) {
        if (headers.size() == 0) {
            return;
        }
        Set<String> headerNames = headers.names();
        LOGD(TAG, "******************** <<<<<<<<<<  LOGGING HEADERS >>>>>>>>>>  ********************");
        for (String headerName : headerNames) {
            LOGD(TAG, headerName + " = " + headers.get(headerName));
        }
        LOGD(TAG, "xxxxxxxxxxxxxxxxxxxx <<<<<<<<<<  LOGGING HEADERS >>>>>>>>>>  xxxxxxxxxxxxxxxxxxxx");
    }

    private void logRequestMisc(Request request) {
        LOGD(TAG, "REQUEST URL          = " +request.url().toString());
        LOGD(TAG,"REQUEST METHOD        = "+request.method());
        LOGD(TAG,"REQUEST BODY          = "+request.body());
    }

    private void logResponseMisc(Response response) {
        LOGD(TAG, "RESPONSE CODE          = " +response.code());
        /*try {
            LOGD(TAG,"RESPONSE BODY          = "+new String(response.body().bytes()));
            response.body().byteStream().reset();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
