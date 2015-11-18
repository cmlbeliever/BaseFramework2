package com.cml.common.baseframework.constant;

/**
 * Created by cmlBeliever on 2015/11/17.
 * api访问常量信息
 */
public interface ApiConstant {
    String BASE_URL = "https://raw.githubusercontent.com/cmlbeliever/BaseFramework2/master/data/";

    interface Api {
        String USER="user.json";
        String LOGIN = "/data.json";
        String INIT = "/init";
    }
}
