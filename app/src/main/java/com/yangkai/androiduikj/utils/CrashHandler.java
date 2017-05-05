package com.yangkai.androiduikj.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.HandlerThread;


import com.orhanobut.logger.Logger;
import com.yangkai.androiduikj.base.BaseApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kale
 * @date 2016/7/1
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //private static final String TAG = "CrashHandler";

    private static CrashHandler sInstance = new CrashHandler();

    //系统默认的异常处理（默认情况下，系统会终止当前的异常程序）  
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;

    public static CrashHandler getInstance() {
        return sInstance;
    }

    //这里主要完成初始化工作  
    public void init(Context context) {
        this.mContext = context;
        //获取系统默认的异常处理器  
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前实例设为系统默认的异常处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug  
        uploadExceptionToServer(thread, ex);
        //打印出当前调用栈信息  
        ex.printStackTrace();

        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己  
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }

    }
    //记录日志到本地
    private void uploadExceptionToServer(Thread thread, final Throwable ex) {
        Logger.e(ex,"uncaughtException .......");
        //Toast.makeText(BaseApplication.getInstance(), R.string.error_uncaught, Toast.LENGTH_SHORT).show();
        //退出程序
        BaseApplication.getInstance().exitApp();
        final HandlerThread promptThread = new HandlerThread("PromptThread") {
            @Override
            public void run() {
                super.run();
                StringBuilder fileName = new StringBuilder();
                Map<String, String> deviceInfo = collectDeviceInfo(mContext);

                String timeStr = DateUtil.getCurrDateStr("yyyyMMddHHmmss");

                fileName.append("crash-")
                        .append(timeStr).append(".log");
                String storageState = Environment.getExternalStorageState();
                String savePath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/glsqp/log";
                if (!Environment.MEDIA_MOUNTED.equals(storageState)) {
                    Logger.e("无法日志，请检查SD卡是否挂载！");
                    return;
                }
                File savedir = new File(savePath);
                if (!savedir.exists()) {
                    savedir.mkdirs();
                }
                File logFile = new File(savePath, fileName.toString().trim());
                try {
                    FileWriter fw = new FileWriter(logFile);
                    fw.write(deviceInfo.toString() + "\n");
                    fw.write(throwableToString(ex));
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        promptThread.start();
        try {
            Thread.sleep(2000);
            promptThread.quit();//Ask the currently running looper to quit.
            promptThread.join();//阻塞主线程，以便Toast长时间显示。
            UtilTools.exit(mContext, true);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private Map<String, String> collectDeviceInfo(Context ctx) {
        Map<String, String> infos = new HashMap<>();
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return infos;
    }

    /**
     * 将异常消息对象转换成字符串信息
     *
     * @param ex
     * @return
     */
    private String throwableToString(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }
}  