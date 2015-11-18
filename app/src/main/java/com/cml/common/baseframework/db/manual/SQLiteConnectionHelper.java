package com.cml.common.baseframework.db.manual;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SQLiteConnectionHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteConnectionHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "lawson2.db";

    /**
     * 在App版本升级情况下，如果DB的schema发生变更，需要对db的表定义进行升级，需要修改此版本号，以触发 onUpgrade方法，进行db变更
     */
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_SQL_PATH = "schema";

    public static final String DATABASE_CREATE_SQL = "schema.sql";

    private AssetManager assets = null;

    private static SQLiteConnectionHelper helper;

    public static final SQLiteConnectionHelper getInstance(Context context) {

        synchronized (SQLiteConnectionHelper.class) {
            if (null == helper) {
                helper = new SQLiteConnectionHelper(context.getApplicationContext());
            }
        }
        return helper;
    }

    public SQLiteConnectionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        assets = context.getAssets();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCrateTable");
        executeSchema(db, DATABASE_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database
        int changeCnt = newVersion - oldVersion;
        for (int i = 0; i < changeCnt; i++) {
            String schemaName = "update" + (oldVersion + i) + "_" + (oldVersion + i + 1) + ".sql";
            executeSchema(db, schemaName);
        }
    }


    protected void executeSchema(SQLiteDatabase db, String schemaName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(assets.open(DATABASE_SQL_PATH + "/" + schemaName)));
            String line;
            StringBuffer sqlb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                if (line.trim().endsWith(";")) {
                    sqlb.append(line.trim().replace(";", ""));
                    String sql = sqlb.toString();
                    db.execSQL(sql);
                    sqlb = new StringBuffer();
                } else {
                    sqlb.append(line);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed executing " + schemaName, e);
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e(TAG, "", e);
            }
        }
    }

}
