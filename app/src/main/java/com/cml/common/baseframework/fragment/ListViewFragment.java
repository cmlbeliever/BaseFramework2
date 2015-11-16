package com.cml.common.baseframework.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.cml.common.baseframework.R;
import com.cml.common.baseframework.helper.PullRefreshHelper;
import com.cml.common.baseframework.model.PageModel;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.trello.rxlifecycle.FragmentEvent;

/**
 *
 */
public class ListViewFragment extends BaseFragment {

    private static final int PageSize = 20;

    private PageModel pageModel;

    public ListViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PullToRefreshListView listView = (PullToRefreshListView) view.findViewById(R.id.listview);

        pageModel = new PageModel(1, 20);

        SimpleAdapter adapter = new SimpleAdapter(getContext(), pageModel.getDummyData(), android.R.layout.simple_list_item_1, new String[]{"text"}, new int[]{
                android.R.id.text1
        });

        new PullRefreshHelper(listView, adapter, pageModel).setUp(null);

        bindUntilEvent(FragmentEvent.DESTROY);
    }
}
