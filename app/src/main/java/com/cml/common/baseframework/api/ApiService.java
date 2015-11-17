package com.cml.common.baseframework.api;

import com.cml.common.baseframework.api.model.UserApiResponse;
import com.cml.common.baseframework.constant.ApiConstant;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by cmlBeliever on 2015/11/17.
 * API请求
 */
public interface ApiService {

    @GET(ApiConstant.Api.USER)
    Observable<UserApiResponse> getUsers();
}
