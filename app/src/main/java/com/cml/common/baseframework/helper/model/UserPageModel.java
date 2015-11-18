package com.cml.common.baseframework.helper.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.cml.common.baseframework.api.ApiManager;
import com.cml.common.baseframework.api.model.UserApiResponse;
import com.cml.common.baseframework.db.model.UserModel;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class UserPageModel extends PageModel {

    public static final String TAG = UserPageModel.class.getSimpleName();

    public UserPageModel(int pageSize, int currentPage) {
        super(pageSize, currentPage, UserModel.class);
    }

    /**
     * 根据随机数randomSeek，随机生成插入个数,最少插入一个
     *
     * @param randomSeek
     * @return
     */
    public int insertPageUsers(int randomSeek) {

        int size = (int) (Math.random() * randomSeek * 10 + 1);

        if (size < 1) {
            size = 1;
        }
        int userCount = new Select().from(UserModel.class).count();

        //开启事物批量插入
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 1; i <= size; i++) {
                String username = "username" + (i + userCount);
                String password = "password" + (i + userCount);

                UserModel model = new UserModel();
                model.username = username;
                model.password = password;

                model.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return size;
    }

    @Override
    public Observable<? extends List<? extends Model>> getPageData() {
        return Observable.create(new Observable.OnSubscribe<List<UserModel>>() {
            @Override
            public void call(Subscriber<? super List<UserModel>> subscriber) {
                int from = (currentPage - 1) * pageSize;
                List<UserModel> datas = new Select().from(tableClass).offset(from).limit(pageSize).execute();
                subscriber.onNext(datas);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable loadFromApi(Observable.Transformer lifecycler) {
        Observable<UserApiResponse> userApiResponseObservable = ApiManager.getApiService().getUsers();
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

    @Override
    public Observable<Integer> insertPageData() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                //插入db数据
                int size = insertPageUsers(pageSize);
                subscriber.onNext(size);
                subscriber.onCompleted();
            }
        });
    }

}
