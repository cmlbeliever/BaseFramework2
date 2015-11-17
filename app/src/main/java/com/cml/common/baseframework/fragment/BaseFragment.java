package com.cml.common.baseframework.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cml.common.baseframework.activity.BaseActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by cmlBeliever on 2015/11/16.
 * 基础fragment，集成butterKnife注入功能
 */
public abstract class BaseFragment extends RxFragment {

    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(BaseActivity.FloatClickEvent clickEvent) {
        Toast.makeText(getContext(), "点击了floatbutton", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayoutResourceId();
}
