package com.cml.common.baseframework.helper.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.cml.common.baseframework.db.model.UserModel;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class UserPageModel extends PageModel {

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

        int size = (int) (Math.random() * randomSeek*10 + 1);

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
