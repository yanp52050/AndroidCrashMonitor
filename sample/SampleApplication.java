package com.example.crashmonitor.sample;

import android.app.Application;
import android.util.Log;

import com.yan.crashmonitor.CrashInfo;
import com.yan.crashmonitor.CrashMonitor;

/**
 * Sample Application showing how to use AndroidCrashMonitor.
 */
public class SampleApplication extends Application {
    private static final String TAG = "SampleApplication";
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize CrashMonitor
        CrashMonitor.init(this)
            .enableJavaCrash(true)      // Capture Java exceptions
            .enableNativeCrash(true)    // Capture native crashes (SIGSEGV, etc.)
            .enableAnrMonitor(true)     // Monitor for ANR situations
            .setCrashCallback(crashInfo -> {
                // Handle crash - upload to server, show notification, etc.
                handleCrash(crashInfo);
            })
            .start();
        
        Log.i(TAG, "CrashMonitor initialized");
    }
    
    private void handleCrash(CrashInfo crashInfo) {
        Log.e(TAG, "Crash detected: " + crashInfo.getType());
        Log.e(TAG, "Exception: " + crashInfo.getException());
        Log.e(TAG, "Message: " + crashInfo.getMessage());
        
        // TODO: Upload crash report to server
        // uploadCrashToServer(crashInfo);
        
        // TODO: Show user-friendly notification
        // showCrashNotification(crashInfo);
    }
}
