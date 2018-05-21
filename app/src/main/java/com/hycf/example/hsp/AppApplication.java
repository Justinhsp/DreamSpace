package com.hycf.example.hsp;

import android.content.Context;

import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.LogUtils;

public class AppApplication extends BaseApplication {

    private static AppApplication app;
    private Context mContext;



    public static AppApplication getInstance() {
        return app;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mContext = getApplicationContext();
        //初始化logger
        //LogUtils.logInit(BuildConfig.DEBUG);
    }
}
