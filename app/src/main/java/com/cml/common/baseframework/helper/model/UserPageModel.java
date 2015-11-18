package com.cml.common.baseframework.helper.model;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.cml.common.baseframework.db.model.UserModel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class UserPageModel extends PageModel {

    public static final String TAG = UserPageModel.class.getSimpleName();

    public UserPageModel(int pageSize, int currentPage) {
        super(pageSize, currentPage, UserModel.class);
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


}
