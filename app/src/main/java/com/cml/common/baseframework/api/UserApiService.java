package com.cml.common.baseframework.api;

import com.cml.common.baseframework.api.response.UserApiResponse;
import com.cml.common.baseframework.db.model.UserModel;
import com.socks.library.KLog;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2015/11/18.
 */
public class UserApiService {

    private static final String TAG = UserApiService.class.getSimpleName();

    /**
     * 获取所有用户信息
     *
     * @param lifecycler
     * @return
     */
    public static Observable<Boolean> getUsers(Observable.Transformer<Boolean, Boolean> lifecycler) {
        Observable<UserApiResponse> userApiResponseObservable = ApiManager.getApiService().getUsers("name");
        return userApiResponseObservable.flatMap(new Func1<UserApiResponse, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(UserApiResponse userApiResponse) {
                // 差分更新DB数据
                int affectRows = UserModel.insertOrUpdate(userApiResponse.getUsers());
                boolean hasDataChange = affectRows > 0;
                KLog.i(TAG, "api request callback==(map)===>" + userApiResponse + ",has data changed : " + hasDataChange + "," + ",threadId:" + Thread.currentThread().getId());

                return Observable.just(hasDataChange);
            }
        }).compose(lifecycler).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
