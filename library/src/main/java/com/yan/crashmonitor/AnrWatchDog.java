package com.yan.crashmonitor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * ANR Watchdog - detects Application Not Responding situations.
 * 
 * Works by posting a delayed message to the main thread and checking if it's processed.
 */
public class AnrWatchDog extends Thread {
    private static final String TAG = "AnrWatchDog";
    private static final long ANR_TIMEOUT = 5000; // 5 seconds
    
    private final Context context;
    private final CrashCallback callback;
    private final Handler mainHandler;
    private volatile boolean running = true;
    private volatile long lastRespondTime;
    
    public AnrWatchDog(Context context, CrashCallback callback) {
        this.context = context;
        this.callback = callback;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.lastRespondTime = System.currentTimeMillis();
        setName("AnrWatchDog");
        setDaemon(true);
    }
    
    @Override
    public void run() {
        Log.d(TAG, "ANR watchdog started");
        
        while (running) {
            // Post a check message to main thread
            mainHandler.post(() -> {
                lastRespondTime = System.currentTimeMillis();
            });
            
            try {
                Thread.sleep(ANR_TIMEOUT);
            } catch (InterruptedException e) {
                Log.w(TAG, "Interrupted", e);
                continue;
            }
            
            // Check if main thread responded
            long currentTime = System.currentTimeMillis();
            long timeSinceLastRespond = currentTime - lastRespondTime;
            
            if (timeSinceLastRespond > ANR_TIMEOUT) {
                Log.w(TAG, "Possible ANR detected! Main thread blocked for " 
                    + timeSinceLastRespond + "ms");
                
                CrashInfo crashInfo = buildAnrInfo();
                CrashStorage.save(context, crashInfo);
                
                if (callback != null) {
                    callback.onCrash(crashInfo);
                }
            }
        }
    }
    
    private CrashInfo buildAnrInfo() {
        CrashInfo crashInfo = new CrashInfo(CrashInfo.CrashType.ANR);
        
        // Get main thread stack trace
        Looper mainLooper = Looper.getMainLooper();
        Thread mainThread = mainLooper.getThread();
        StackTraceElement[] stackTrace = mainThread.getStackTrace();
        
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }
        crashInfo.setStackTrace(sb.toString());
        crashInfo.setException("ANR");
        crashInfo.setMessage("Main thread blocked for more than " + ANR_TIMEOUT + "ms");
        
        return crashInfo;
    }
    
    public void stopWatchdog() {
        running = false;
        interrupt();
    }
}
