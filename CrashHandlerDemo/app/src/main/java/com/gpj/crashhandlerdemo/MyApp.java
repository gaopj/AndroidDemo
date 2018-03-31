package com.gpj.crashhandlerdemo;

import android.app.Application;

/**
 * Created by v-pigao on 3/31/2018.
 */

public class MyApp extends Application {
    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public static MyApp getInstance() {
        return sInstance;
    }

}
