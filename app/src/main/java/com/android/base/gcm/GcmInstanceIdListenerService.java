package com.android.base.gcm;

import com.android.base.utils.LogUtils;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by gagandeep on 1/7/15.
 */
public class GcmInstanceIdListenerService extends InstanceIDListenerService {

    private final static String TAG = LogUtils.makeLogTag(GcmInstanceIdListenerService.class);
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        LogUtils.LOGD(TAG, "GCMInstanceIDListenerService.onTokenRefresh");
        //GcmRegistrationManager.getInstance(getApplicationContext()).refreshToken();
    }
}
