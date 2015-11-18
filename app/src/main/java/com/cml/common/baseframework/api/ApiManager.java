package com.cml.common.baseframework.api;

import com.cml.common.baseframework.constant.ApiConstant;
import com.socks.library.KLog;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class ApiManager {

    private static final String TAG = ApiManager.class.getSimpleName();

    private static Retrofit retrofit;
    private static ApiService apiService;

    public static void init(String baseUrl) {
        if (null == retrofit) {

            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
            //retrofit.client().setCache(new Cache());
            retrofit.client().setConnectTimeout(ApiConstant.DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            retrofit.client().interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    KLog.i(TAG, "requestUrl==>" + request.urlString() + ",请求body---->" + request.body().contentType()+","+chain.connection()+",,"+request.headers());
                    return chain.proceed(request);
                }
            });
        }
    }

    public static <T> T createService(Class<T> target) {
        return retrofit.create(target);
    }

    public static ApiService getApiService() {
        if (null != apiService) {
            return apiService;
        }
        return retrofit.create(ApiService.class);
    }
}
