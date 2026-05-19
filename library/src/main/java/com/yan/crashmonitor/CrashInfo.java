package com.yan.crashmonitor;

import java.io.Serializable;

/**
 * Represents crash information.
 */
public class CrashInfo implements Serializable {
    
    public enum CrashType {
        JAVA_CRASH,
        NATIVE_CRASH,
        ANR
    }
    
    private CrashType type;
    private long timestamp;
    private String exception;
    private String message;
    private String stackTrace;
    private String deviceModel;
    private String osVersion;
    private int sdkVersion;
    private String appVersion;
    private String processName;
    
    public CrashInfo(CrashType type) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    
    public CrashType getType() { return type; }
    public void setType(CrashType type) { this.type = type; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    public String getException() { return exception; }
    public void setException(String exception) { this.exception = exception; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
    
    public String getDeviceModel() { return deviceModel; }
    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }
    
    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
    
    public int getSdkVersion() { return sdkVersion; }
    public void setSdkVersion(int sdkVersion) { this.sdkVersion = sdkVersion; }
    
    public String getAppVersion() { return appVersion; }
    public void setAppVersion(String appVersion) { this.appVersion = appVersion; }
    
    public String getProcessName() { return processName; }
    public void setProcessName(String processName) { this.processName = processName; }
    
    @Override
    public String toString() {
        return "CrashInfo{" +
                "type=" + type +
                ", timestamp=" + timestamp +
                ", exception='" + exception + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
