package com.example.joy.smartbutler.application;

import android.app.Application;
import android.content.Context;

import com.example.joy.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Created by joy on 2018/3/22.
 */

public class BaseApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        Bmob.initialize(this, StaticClass.BMOB_APPLICATION_ID);

        mContext=getApplicationContext();
    }

    public static Context getMyContext(){
        return mContext;
    }
}
