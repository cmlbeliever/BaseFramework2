package com.cml.common.baseframework.helper.model;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.cml.common.baseframework.db.model.UserModel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cmlBeliever on 2015/11/16.
 * 分页model
 */
public abstract class PageModel {
    public int currentPage;
    public final int pageSize;
    public final Class<? extends Model> tableClass;

    public PageModel(int pageSize, int currentPage, Class<? extends Model> tableClass) {
        this.pageSize = pageSize;
        this.tableClass = tableClass;
        this.currentPage = currentPage;
    }

    public void nextPage() {
        currentPage++;
    }

    public void reset(int currentPage) {
        this.currentPage = currentPage;
    }


    public void reset() {
        this.currentPage = 1;
    }

    public Observable<? extends List<? extends Model>> getPageData() {
        return Observable.create(new Observable.OnSubscribe<List<UserModel>>() {
            @Override
            public void call(Subscriber<? super List<UserModel>> subscriber) {
                int from = (currentPage - 1) * pageSize;
                List<UserModel> datas = new Select().from(tableClass).offset(from).limit(pageSize).execute();
                subscriber.onNext(datas);
                subscriber.onCompleted();
            }
        });
    }

    public abstract Observable<Boolean> loadFromApi(Observable.Transformer lifecycler);

    public abstract Observable<Integer> insertPageData();
}
