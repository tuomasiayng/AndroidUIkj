package com.yangkai.androiduikj.base;

import android.support.multidex.MultiDexApplication;

/**
 * Created by yangkai on 2017/5/3.
 */

public abstract class BaseApplication extends MultiDexApplication {

    protected static BaseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static BaseApplication getInstance(){
        return instance;
    }

    public abstract void exitApp();
}
