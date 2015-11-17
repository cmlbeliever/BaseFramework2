package com.cml.common.baseframework.activity;

import android.view.MenuItem;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class BaseActivity extends RxAppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
