package com.cml.common.baseframework.db;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class a {
    private static a ourInstance = new a();

    public static a getInstance() {
        return ourInstance;
    }

    private a() {
    }
}
