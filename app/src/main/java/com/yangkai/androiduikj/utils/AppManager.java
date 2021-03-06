package com.yangkai.androiduikj.utils;

import android.app.Activity;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.Stack;

/**
 *堆栈管理器
 * 
 */
public class AppManager {

	private static AppManager instance;// 单例
	private static Stack<Activity> activityStack;// 自定义Activity栈

	/**
	 * 单一实例
	 */
	public synchronized static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();// 单例
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public synchronized void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<>();
		}
		Logger.d("----add Activity--" + activity.getLocalClassName());
		activityStack.add(activity);
	}

	/**
	 * 结束所有Activity
	 */
	public synchronized void finishAllActivity() {
		int lenght = activityStack.size();
		for (int i = 0; i < lenght; i++) {
			Activity a = activityStack.pop();
			if (null != a) {
				finishActivity(a);
			}
		}
		activityStack.clear();
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		if (activityStack == null || activityStack.isEmpty()) {
			return;
		}
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null && !activity.isFinishing()) {
			Logger.d("---finish Activity--- " + activity.getLocalClassName());
			activityStack.remove(activity);
			activity.finish();
		}
	}

	/**
	 * 获取指定的Activity
	 * 
	 * @author kymjs
	 */
	public static Activity getActivity(Class<?> cls) {
		if (activityStack != null)
			for (Activity activity : activityStack) {
				if (activity.getClass().equals(cls)) {
					return activity;
				}
			}
		return null;
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			// 接受所有Activity
			finishAllActivity();
			// 杀死当前应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}