package com.cml.common.baseframework.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cml.common.baseframework.R;
import com.cml.common.baseframework.activity.BaseActivity;
import com.cml.common.baseframework.activity.UserInfoActivity;
import com.cml.common.baseframework.api.volley.service.VolleyUserApiService;
import com.cml.common.baseframework.db.model.UserModel;
import com.cml.common.baseframework.fragment.adapter.UserAdapter;
import com.cml.common.baseframework.helper.CommonTransformer;
import com.cml.common.baseframework.helper.PullRefreshHelper;
import com.cml.common.baseframework.helper.PullRefreshHelper.OnPullListener;
import com.cml.common.baseframework.helper.model.UserPageModel;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import rx.Observable;

/**
 *
 */
public class ListViewFragment extends BaseFragment implements OnPullListener {

    private static final String TAG = ListViewFragment.class.getSimpleName();
    private static final int PageSize = 20;

    @Bind(R.id.listview)
    PullToRefreshListView listview;

    private Observable.Transformer transformer;

    public ListViewFragment() {

    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_list_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserPageModel pageModel = new UserPageModel(PageSize, 1);
        ArrayAdapter<UserModel> adapter = new UserAdapter(getContext());

        transformer = bindUntilEvent(FragmentEvent.DESTROY);

        new PullRefreshHelper(listview, adapter, pageModel, transformer, this).setUp(null);

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

    @Override
    public Observable<Boolean> onPull() {
        //TODO 使用Retrofit方式请求数据
//        return UserApiService.getUsers(new CommonTransformer<Boolean>(transformer));
        //TODO 使用Volley方式请求数据
        return VolleyUserApiService.getUsers(new CommonTransformer<Boolean>(transformer));
    }
}
