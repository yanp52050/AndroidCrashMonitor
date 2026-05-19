package com.yan.crashmonitor;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Local crash log storage.
 */
public class CrashStorage {
    private static final String TAG = "CrashStorage";
    private static final String CRASH_DIR = "crash_logs";
    private static final String LOG_SUFFIX = ".crash";
    
    /**
     * Save crash info to local file.
     */
    public static void save(Context context, CrashInfo crashInfo) {
        try {
            File crashDir = new File(context.getFilesDir(), CRASH_DIR);
            if (!crashDir.exists()) {
                crashDir.mkdirs();
            }
            
            String filename = generateFilename(crashInfo);
            File crashFile = new File(crashDir, filename);
            
            FileWriter writer = new FileWriter(crashFile);
            writer.write(formatCrashInfo(crashInfo));
            writer.flush();
            writer.close();
            
            Log.i(TAG, "Crash log saved: " + crashFile.getAbsolutePath());
            
        } catch (IOException e) {
            Log.e(TAG, "Failed to save crash log", e);
        }
    }
    
    private static String generateFilename(CrashInfo crashInfo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        String timestamp = sdf.format(new Date(crashInfo.getTimestamp()));
        return crashInfo.getType().name() + "_" + timestamp + LOG_SUFFIX;
    }
    
    private static String formatCrashInfo(CrashInfo crashInfo) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== Crash Report ===\n");
        sb.append("Type: ").append(crashInfo.getType()).append("\n");
        sb.append("Time: ").append(new Date(crashInfo.getTimestamp())).append("\n");
        sb.append("\n--- Device Info ---\n");
        sb.append("Model: ").append(crashInfo.getDeviceModel()).append("\n");
        sb.append("OS: Android ").append(crashInfo.getOsVersion()).append("\n");
        sb.append("SDK: ").append(crashInfo.getSdkVersion()).append("\n");
        sb.append("\n--- App Info ---\n");
        sb.append("Version: ").append(crashInfo.getAppVersion()).append("\n");
        sb.append("Process: ").append(crashInfo.getProcessName()).append("\n");
        sb.append("\n--- Exception ---\n");
        sb.append("Exception: ").append(crashInfo.getException()).append("\n");
        sb.append("Message: ").append(crashInfo.getMessage()).append("\n");
        sb.append("\n--- Stack Trace ---\n");
        sb.append(crashInfo.getStackTrace());
        
        return sb.toString();
    }
    
    /**
     * Get all crash log files.
     */
    public static File[] getCrashLogs(Context context) {
        File crashDir = new File(context.getFilesDir(), CRASH_DIR);
        if (crashDir.exists()) {
            return crashDir.listFiles();
        }
        return new File[0];
    }
    
    /**
     * Clear all crash logs.
     */
    public static void clearCrashLogs(Context context) {
        File crashDir = new File(context.getFilesDir(), CRASH_DIR);
        if (crashDir.exists()) {
            File[] files = crashDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}
