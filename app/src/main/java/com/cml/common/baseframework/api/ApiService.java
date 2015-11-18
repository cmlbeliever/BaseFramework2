package com.cml.common.baseframework.api;

import com.cml.common.baseframework.api.response.UserApiResponse;
import com.cml.common.baseframework.constant.ApiConstant;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by cmlBeliever on 2015/11/17.
 * API请求
 */
public interface ApiService {

    @POST(ApiConstant.Api.USER)
    @FormUrlEncoded
    Observable<UserApiResponse> getUsers(@Field("name") String name);
}
