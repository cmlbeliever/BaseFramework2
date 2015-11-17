package com.cml.common.baseframework.helper;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.cml.common.baseframework.MyApplication;
import com.cml.common.baseframework.helper.model.PageModel;
import com.cml.common.baseframework.util.AppUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2015/11/16.
 * 下拉刷新插件帮助类，作为demo工程时，实现下拉往db插入pageSize条数据，上拉加载db数据，以此来模拟网络数据请求与本地加载
 */
public class PullRefreshHelper {

    private static final String TAG = PullRefreshHelper.class.getSimpleName();

    private PullToRefreshListView listView;
    private ArrayAdapter<? extends Model> adapter;
    private PageModel pageModel;
    private Observable.Transformer<Object, Object> transformer;//绑定请求的生命周期

    /**
     * 用户下拉与上拉事件监听，完成数据加载与导入
     */
    private PullToRefreshBase.OnRefreshListener2 onRefreshListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        @Override
        public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
            pageModel.loadFromApi(transformer).doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Toast.makeText(MyApplication.getContext(), "出错了，网络加载失败", Toast.LENGTH_LONG).show();
                }
            }).onErrorResumeNext(Observable.just(false)).doOnNext(new Action1<Boolean>() {
                @Override
                public void call(Boolean hasData) {
                    Toast.makeText(MyApplication.getContext(), "返回数据：hasData:"+hasData, Toast.LENGTH_LONG).show();
                    //有数据变化，重新加载listview数据
                    if (hasData) {
                        //服务器返回的数据处理完毕后，
                        //1、清空数据，重新加载本地数据
                        // 加载数据、
                        pageModel.reset();
                        adapter.clear();
                        loadLocalData();
                    } else {
                        refreshCompleted();
                    }
                }
            }).compose(transformer).subscribe();
            //插入db数据结束后关闭加载效果
//            Observable<Integer> serverDataObserver = pageModel.insertPageData();
//            serverDataObserver.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnCompleted(new Action0() {
//                @Override
//                public void call() {
//                    //服务器返回的数据处理完毕后，
//                    //1、清空数据，重新加载本地数据
//                    // 加载数据、
//                    pageModel.reset();
//                    adapter.clear();
//                    loadLocalData();
//                }
//            }).compose(transformer).subscribe();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            // 加载更多数据
            pageModel.nextPage();
            loadLocalData();
        }
    };

    /**
     * 加载本地数据
     */
    private void loadLocalData() {
        pageModel.getPageData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<List<? extends Model>>() {
            @Override
            public void call(List models) {
                KLog.d(TAG, "---doOnSubscribe--->获取本地数据长度，数据长度：" + (models == null ? 0 : models.size()) + ",threadID:" + Thread.currentThread().getId());
                if (null != models) {
                    adapter.addAll(models);
                    adapter.notifyDataSetChanged();
                }
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                refreshCompleted();
                //没有更多数据了
                if (adapter.getCount() % pageModel.pageSize != 0) {
                    listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                } else {
                    listView.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }
        }).compose(transformer).subscribe();
    }

    public PullRefreshHelper(PullToRefreshListView listView, ArrayAdapter adapter, PageModel pageModel, Observable.Transformer<Object, Object> transformer) {
        this.listView = listView;
        this.adapter = adapter;
        this.pageModel = pageModel;
        this.transformer = transformer;
    }

    /**
     * 进行初始化配置
     *
     * @param mode
     */
    public void setUp(PullToRefreshBase.Mode mode) {

        listView.setOnRefreshListener(onRefreshListener);

        if (null == mode) {
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(mode);
        }

        listView.setAdapter(adapter);

        //先加载本地缓存数据
        loadLocalData();

        //网络正常，从服务器获取数据
        if (AppUtil.hasNetwork()) {
            listView.setRefreshing();
        }
    }

    /**
     * 结束加载效果
     */
    private void refreshCompleted() {
        if (listView.isRefreshing()) {
            listView.onRefreshComplete();
        }
    }
}
