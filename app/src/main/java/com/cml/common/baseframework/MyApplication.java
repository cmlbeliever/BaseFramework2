package com.cml.common.baseframework;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.cml.common.baseframework.api.ApiManager;
import com.cml.common.baseframework.api.volley.ImageCacheManager;
import com.cml.common.baseframework.api.volley.ImageCaches;
import com.cml.common.baseframework.api.volley.RequestManager;
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

        //使用Volley作为网络请求层时需要调用initVolley()
        initVolley();

    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化volley以及图片缓存信息
     */
    private void initVolley() {
        // 设定volley缓存
        RequestManager.init(this);
        createImageCache();
    }

    /**
     * Create the image cache. Uses Memory Cache by default. Change to Disk for
     * a Disk based LRU implementation.
     */
    private void createImageCache() {

        /**
         * Create the image cache. Uses Memory Cache by default. Change to Disk
         * for a Disk based LRU implementation. 设置缓存为100MB
         */
        ImageCacheManager.getInstance().init(this, this.getPackageCodePath(),
                ImageCaches.Disk.Size, ImageCaches.Disk.Format, ImageCaches.Disk.Quality,
                ImageCacheManager.CacheType.DISK);
    }

}
