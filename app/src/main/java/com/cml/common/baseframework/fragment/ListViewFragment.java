package com.cml.common.baseframework.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cml.common.baseframework.R;
import com.cml.common.baseframework.activity.BaseActivity;
import com.cml.common.baseframework.activity.UserInfoActivity;
import com.cml.common.baseframework.db.model.UserModel;
import com.cml.common.baseframework.fragment.adapter.UserAdapter;
import com.cml.common.baseframework.helper.PullRefreshHelper;
import com.cml.common.baseframework.helper.model.UserPageModel;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;

/**
 *
 */
public class ListViewFragment extends BaseFragment {

    private static final int PageSize = 20;

    private UserPageModel pageModel;

    @Bind(R.id.listview)
    PullToRefreshListView listview;

    public ListViewFragment() {

    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_list_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageModel = new UserPageModel(20, 1);

        ArrayAdapter<UserModel> adapter = new UserAdapter(getContext());

        new PullRefreshHelper(listview, adapter, pageModel, bindUntilEvent(FragmentEvent.STOP)).setUp(null);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel userModel = (UserModel) parent.getItemAtPosition(position);
                UserInfoActivity.startActivity(getContext(), userModel.getId());
            }
        });
    }

    /**
     * 获取FloatButton点击事件，完成页面自动下拉功能
     *
     * @param clickEvent
     */
    @Override
    public void onEvent(BaseActivity.FloatClickEvent clickEvent) {
        if (!listview.isRefreshing()) {
            listview.setRefreshing();
        }
    }
}
