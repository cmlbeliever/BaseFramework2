package com.cml.common.baseframework;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.cml.common.baseframework.api.ApiManager;
import com.cml.common.baseframework.constant.ApiConstant;
import com.socks.library.KLog;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        ActiveAndroid.initialize(getApplicationContext());
        ApiManager.init(ApiConstant.BASE_URL);

        KLog.init(true);
        KLog.i(TAG, "application init success===>");
    }

    public static Context getContext() {
        return context;
    }
}
