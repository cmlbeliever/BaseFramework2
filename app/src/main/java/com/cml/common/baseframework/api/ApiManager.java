package com.cml.common.baseframework.api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class ApiManager {

    private static Retrofit retrofit;
    private static ApiService apiService;

    public static void init(String baseUrl) {
        if (null == retrofit) {

            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

//            OkHttpClient client = new OkHttpClient();
//            client.interceptors().add(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    return null;
//                }
//            });
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
