package com.cml.common.baseframework.api.volley.request;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cml.common.baseframework.api.volley.VolleyConstant;

/**
 * 修改volley默认的重试策略
 */
public class StringRequestRetry extends StringRequest {

	/**
	 * {@inheritDoc}
	 */
	public StringRequestRetry(int method, String url, Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(VolleyConstant.Network.TIMEOUT,
				VolleyConstant.Network.RETRY, 0));
	}

	/**
	 * {@inheritDoc}
	 */
	public StringRequestRetry(String url, Listener<String> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(VolleyConstant.Network.TIMEOUT,
				VolleyConstant.Network.RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}
}
