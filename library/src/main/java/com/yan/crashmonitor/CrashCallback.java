package com.yan.crashmonitor;

/**
 * Callback interface for crash events.
 */
public interface CrashCallback {
    
    /**
     * Called when a crash is captured.
     * 
     * @param crashInfo The crash information
     */
    void onCrash(CrashInfo crashInfo);
}
