package com.cml.common.baseframework.api.client;

import retrofit.RestAdapter;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class ApiManager {

    private static RestAdapter adapter;

    public static void init(String baseUrl, RestAdapter.LogLevel logLevel) {
        if (null == adapter) {
            adapter = new RestAdapter.Builder().setEndpoint(baseUrl).setLogLevel(logLevel).build();
        }
    }

    public static <T> T createService(Class<T> target) {
        return adapter.create(target);
    }
}
