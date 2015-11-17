package com.cml.common.baseframework.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cml.common.baseframework.MyApplication;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class PrefUtils {
    private static final String KEY_USER_INDEX = "key_user_index";

    /**
     * 设置user个数
     *
     * @param index
     */
    public static void setUserIndex(int index) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        preferences.edit().putInt(KEY_USER_INDEX, index).apply();
    }

    /**
     * 返回记录的user数据长度，
     *
     * @return
     */
    public static int getUserIndex() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return preferences.getInt(KEY_USER_INDEX, 0);
    }
}
