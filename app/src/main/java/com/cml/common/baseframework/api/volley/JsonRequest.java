package com.cml.common.baseframework.api.volley;

import com.android.volley.Response;
import com.cml.common.baseframework.api.volley.request.StringRequestRetry;

/**
 * Created by cmlBeliever on 2015/11/18.
 */
public class JsonRequest extends StringRequestRetry {
    public JsonRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public JsonRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
