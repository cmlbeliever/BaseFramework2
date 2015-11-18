package com.cml.common.baseframework.api.volley.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmlBeliever on 2015/11/18.
 */
public class ApiService {
    
    public static final String TAG = ApiService.class.getSimpleName();

    /**
     * 获取所有请求基本参数
     *
     * @return
     */
    public static Map<String, String> getRequestBaseMap() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accessToken", "accessToken");
        return params;
    }
}
