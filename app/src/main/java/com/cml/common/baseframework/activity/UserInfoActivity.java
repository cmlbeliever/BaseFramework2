package com.cml.common.baseframework.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cml.common.baseframework.R;
import com.cml.common.baseframework.db.model.UserModel;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <p> 展示用户个人信息Activity</p>
 * <p>使用RxJava实现DB数据的异步查询与UI线程操作功能</p>
 */
public class UserInfoActivity extends BaseActivity {

    private static final String TAG = "UserInfoActivity";
    private static final String EXTRA_ID = "userId";

    @Bind(R.id.userinfo)
    TextView userinfoView;

    @Bind(R.id.toolbar_img)
    ImageView toolbarImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        long userId = getIntent().getLongExtra(EXTRA_ID, -1);
        final CollapsingToolbarLayout coordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        //查询db信息
        Observable.just(userId).map(new Func1<Long, UserModel>() {
            @Override
            public UserModel call(Long id) {
                KLog.i(TAG, "-------> 查询用户信息！userId=" + id + ",thread:" + Thread.currentThread().getId());
                return UserModel.load(UserModel.class, id);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<UserModel>() {
            @Override
            public void call(UserModel userModel) {
                if (null != userModel) {
                    coordinatorLayout.setTitle(userModel.username);
                    userinfoView.setText("username:" + userModel.username + "\n password:" + userModel.password);
                    Glide.with(UserInfoActivity.this).load(userModel.headIcon).centerCrop().crossFade().into(toolbarImg);
                } else {
                    Toast.makeText(getApplicationContext(), "该用户不存在！", Toast.LENGTH_LONG).show();
                }
                KLog.i(TAG, "-------> 查询用户信息返回！user=" + userModel + ",thread:" + Thread.currentThread().getId());
            }
        }).compose(bindToLifecycle()).subscribe();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void startActivity(Context context, long userId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(EXTRA_ID, userId);
        context.startActivity(intent);
    }
}
