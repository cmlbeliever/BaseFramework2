package com.cml.common.baseframework.helper.model;

import com.activeandroid.Model;

import java.util.List;

import rx.Observable;

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

    /**
     * 获取当前页码的数据，返回数据回调在mainThread，处理在io线程
     *
     * @return
     */
    public abstract Observable<? extends List<? extends Model>> getPageData();

    public abstract Observable<Boolean> loadFromApi(Observable.Transformer lifecycler);

    public abstract Observable<Integer> insertPageData();
}
