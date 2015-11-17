package com.cml.common.baseframework.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.cml.common.baseframework.BuildConfig;
import com.cml.common.baseframework.MyApplication;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class AppUtil {


    private static final String TAG = AppUtil.class.getSimpleName();

    /**
     * 判断当前网络是否是Wifi网络
     *
     * @return
     */
    public static boolean isWifi() {

        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (null != networkInfo && networkInfo.isConnectedOrConnecting()) {
            return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }

        return false;
    }

    /**
     * 判断是否有网络
     *
     * @return
     */
    public static boolean hasNetwork() {

        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (BuildConfig.DEBUG) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (null != networkInfo) {
                Log.i(TAG, networkInfo.getTypeName());
            }
        }

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
