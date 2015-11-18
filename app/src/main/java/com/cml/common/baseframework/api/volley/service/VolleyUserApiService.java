package com.cml.common.baseframework.api.volley.service;

import com.cml.common.baseframework.api.response.BaseApiResponse;
import com.cml.common.baseframework.api.response.UserApiResponse;
import com.cml.common.baseframework.api.volley.request.RxRequest;
import com.cml.common.baseframework.constant.ApiConstant;
import com.cml.common.baseframework.db.model.UserModel;
import com.socks.library.KLog;

import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by cmlBeliever on 2015/11/18.
 */
public class VolleyUserApiService extends ApiService {
    /**
     * 获取所有用户信息
     *
     * @param lifecycler
     * @return
     */
    public static Observable<Boolean> getUsers(Observable.Transformer<Boolean, Boolean> lifecycler) {
        //构造请求参数
        Map<String, String> params = getRequestBaseMap();
        params.put("name", "name");

        String url = ApiConstant.BASE_URL + ApiConstant.Api.USER;

        Observable<BaseApiResponse> userApiResponseObservable = new RxRequest<BaseApiResponse>().postRequest(url, params, UserApiResponse.class);

        return userApiResponseObservable.map(new Func1<BaseApiResponse, Boolean>() {
            @Override
            public Boolean call(BaseApiResponse baseApiResponse) {

                if (null == baseApiResponse) {
                    return false;
                }

                UserApiResponse userApiResponse = (UserApiResponse) baseApiResponse;
                // 差分更新DB数据
                int affectRows = UserModel.insertOrUpdate(userApiResponse.getUsers());
                boolean hasDataChange = affectRows > 0;
                KLog.i(TAG, "api request callback=====>" + userApiResponse + ",has data changed : " + hasDataChange + "," + ",threadId:" + Thread.currentThread().getId());

                return hasDataChange;
            }
        }).compose(lifecycler);
    }
}