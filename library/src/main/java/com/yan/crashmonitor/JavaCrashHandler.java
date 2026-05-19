package com.yan.crashmonitor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Handles Java crashes.
 */
public class JavaCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "JavaCrashHandler";
    
    private final Context context;
    private final CrashCallback callback;
    private final Thread.UncaughtExceptionHandler defaultHandler;
    
    public JavaCrashHandler(Context context, CrashCallback callback) {
        this.context = context;
        this.callback = callback;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e(TAG, "Uncaught exception in thread " + thread.getName(), throwable);
        
        CrashInfo crashInfo = buildCrashInfo(thread, throwable);
        
        // Save crash log locally
        CrashStorage.save(context, crashInfo);
        
        // Notify callback
        if (callback != null) {
            callback.onCrash(crashInfo);
        }
        
        // Call default handler (usually kills the process)
        if (defaultHandler != null) {
            defaultHandler.uncaughtException(thread, throwable);
        }
    }
    
    private CrashInfo buildCrashInfo(Thread thread, Throwable throwable) {
        CrashInfo crashInfo = new CrashInfo(CrashInfo.CrashType.JAVA_CRASH);
        
        // Exception info
        crashInfo.setException(throwable.getClass().getName());
        crashInfo.setMessage(throwable.getMessage());
        crashInfo.setStackTrace(getStackTrace(throwable));
        
        // Device info
        crashInfo.setDeviceModel(Build.MODEL);
        crashInfo.setOsVersion(Build.VERSION.RELEASE);
        crashInfo.setSdkVersion(Build.VERSION.SDK_INT);
        
        // App info
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            crashInfo.setAppVersion(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            crashInfo.setAppVersion("unknown");
        }
        crashInfo.setProcessName(thread.getName());
        
        return crashInfo;
    }
    
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
