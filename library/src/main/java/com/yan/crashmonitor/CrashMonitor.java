package com.yan.crashmonitor;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

/**
 * Main entry point for AndroidCrashMonitor SDK.
 * 
 * Usage:
 * <pre>
 * CrashMonitor.init(context)
 *     .enableJavaCrash(true)
 *     .enableNativeCrash(true)
 *     .enableAnrMonitor(true)
 *     .setCrashCallback(callback)
 *     .start();
 * </pre>
 */
public class CrashMonitor {
    private static final String TAG = "CrashMonitor";
    
    private final Context context;
    private boolean enableJavaCrash = true;
    private boolean enableNativeCrash = true;
    private boolean enableAnrMonitor = true;
    private CrashCallback callback;
    private boolean started = false;
    
    private CrashMonitor(Context context) {
        this.context = context.getApplicationContext();
    }
    
    /**
     * Initialize CrashMonitor with application context.
     */
    public static CrashMonitor init(Context context) {
        return new CrashMonitor(context);
    }
    
    /**
     * Enable or disable Java crash capture.
     */
    public CrashMonitor enableJavaCrash(boolean enable) {
        this.enableJavaCrash = enable;
        return this;
    }
    
    /**
     * Enable or disable native crash capture.
     */
    public CrashMonitor enableNativeCrash(boolean enable) {
        this.enableNativeCrash = enable;
        return this;
    }
    
    /**
     * Enable or disable ANR monitoring.
     */
    public CrashMonitor enableAnrMonitor(boolean enable) {
        this.enableAnrMonitor = enable;
        return this;
    }
    
    /**
     * Set callback for crash events.
     */
    public CrashMonitor setCrashCallback(CrashCallback callback) {
        this.callback = callback;
        return this;
    }
    
    /**
     * Start monitoring.
     */
    public void start() {
        if (started) {
            Log.w(TAG, "Already started");
            return;
        }
        
        if (enableJavaCrash) {
            setupJavaCrashHandler();
        }
        
        if (enableNativeCrash) {
            setupNativeCrashHandler();
        }
        
        if (enableAnrMonitor) {
            setupAnrMonitor();
        }
        
        started = true;
        Log.i(TAG, "CrashMonitor started");
    }
    
    private void setupJavaCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(
            new JavaCrashHandler(context, callback)
        );
        Log.d(TAG, "Java crash handler installed");
    }
    
    private void setupNativeCrashHandler() {
        try {
            System.loadLibrary("crashmonitor");
            nativeInit(context.getFilesDir().getAbsolutePath());
            Log.d(TAG, "Native crash handler installed");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to load native library", e);
        }
    }
    
    private void setupAnrMonitor() {
        AnrWatchDog watchDog = new AnrWatchDog(context, callback);
        watchDog.start();
        Log.d(TAG, "ANR watchdog started");
    }
    
    private native void nativeInit(String crashDir);
}
