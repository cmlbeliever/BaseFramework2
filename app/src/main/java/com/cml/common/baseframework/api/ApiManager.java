package com.cml.common.baseframework.api;

import retrofit.RestAdapter;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class ApiManager {

    private static RestAdapter adapter;
    private static ApiService apiService;

    public static void init(String baseUrl, RestAdapter.LogLevel logLevel) {
        if (null == adapter) {
            adapter = new RestAdapter.Builder().setEndpoint(baseUrl).setLogLevel(logLevel).build();
        }
    }

    public static <T> T createService(Class<T> target) {
        return adapter.create(target);
    }

    public static ApiService getApiService() {
        if (null != apiService) {
            return apiService;
        }
        return adapter.create(ApiService.class);
    }
}
