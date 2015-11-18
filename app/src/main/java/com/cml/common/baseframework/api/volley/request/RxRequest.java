package com.cml.common.baseframework.api.volley.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.cml.common.baseframework.api.response.BaseApiResponse;
import com.cml.common.baseframework.api.volley.RequestManager;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 返回请求的Observable对象
 */
public class RxRequest<T> {

    private static final String TAG = RxRequest.class.getSimpleName();

    /**
     * 设置回调在主线程
     *
     * @param url
     * @param params
     * @param target
     * @return
     */
    public Observable<BaseApiResponse> postRequest(final String url, Map<String, String> params, final Class<? extends BaseApiResponse> target) {
        return Observable.from(getRequestFuture(url, Request.Method.POST, params)).map(new Func1<String, BaseApiResponse>() {
            @Override
            public BaseApiResponse call(String s) {
                if (null != s) {
                    Gson gson = new Gson();
                    return gson.fromJson(s, target);
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private RequestFuture<String> getRequestFuture(final String url, int method, final Map<String, String> params) {
        RequestFuture<String> requestFuture = RequestFuture.newFuture();
        StringRequest request = new StringRequest(method, url, requestFuture, requestFuture) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                KLog.d(TAG, "请求url:" + url + ",params:" + params);
                return params;
            }
        };
        request.setTag(url);
        RequestManager.getRequestQueue().add(request);
        return requestFuture;
    }

}
