package com.yangkai.androiduikj;


import com.yangkai.androiduikj.base.BaseApplication;
import com.yangkai.androiduikj.utils.AppManager;

/**
 * 全局上下文
 * Created by Levin on 17/2/10.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void exitApp() {
        AppManager.getAppManager().finishAllActivity();
    }
}
