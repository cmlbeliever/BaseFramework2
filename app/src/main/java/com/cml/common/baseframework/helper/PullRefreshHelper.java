package com.cml.common.baseframework.helper;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cml.common.baseframework.model.PageModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;
import java.util.Map;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by cmlBeliever on 2015/11/16.
 * 下拉刷新插件帮助类
 */
public class PullRefreshHelper {
    private PullToRefreshListView listView;
    private BaseAdapter adapter;
    private PageModel pageModel;
    private BehaviorSubject behaviorSubject = BehaviorSubject.create();

    private PullToRefreshBase.OnRefreshListener2 onRefreshListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            //TODO
            try {
                Thread.sleep(2000);
                pageModel.nextPage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
            refreshView.onRefreshComplete();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            //TODO 加载更多数据
            pageModel.reset();
            //TODO
            try {

                pageModel.nextPage();
                adapter.notifyDataSetChanged();

                Thread.sleep(2000);
                refreshView.onRefreshComplete();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    public PullRefreshHelper(PullToRefreshListView listView, BaseAdapter adapter, PageModel pageModel) {
        this.listView = listView;
        this.adapter = adapter;
        this.pageModel = pageModel;
    }

    public void setUp(PullToRefreshBase.Mode mode) {
        listView.setOnRefreshListener(onRefreshListener);
        if (null == mode) {
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(mode);
        }

        listView.setAdapter(adapter);
        listView.setRefreshing();

        //订阅数据加载完成事件
        behaviorSubject.subscribe(new Action1<List<Map<String, Object>>>() {
            @Override
            public void call(List<Map<String, Object>> maps) {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
