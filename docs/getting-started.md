# Getting Started

## Installation

Add the dependency to your `build.gradle`:

```gradle
dependencies {
    implementation 'com.yan.crashmonitor:crash-monitor:1.0.0'
}
```

## Basic Setup

### 1. Initialize in Application class

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        CrashMonitor.init(this)
            .enableJavaCrash(true)
            .enableNativeCrash(true)
            .enableAnrMonitor(true)
            .setCrashCallback(crash -> {
                // Handle crash
                Log.e("App", "Crash: " + crash.getException());
            })
            .start();
    }
}
```

### 2. Configuration Options

| Option | Default | Description |
|--------|---------|-------------|
| `enableJavaCrash` | `true` | Capture Java exceptions |
| `enableNativeCrash` | `true` | Capture native crashes (SIGSEGV, etc.) |
| `enableAnrMonitor` | `true` | Monitor for ANR situations |
| `setCrashCallback` | `null` | Callback for crash events |

### 3. Crash Types

#### Java Crash
- Uncaught exceptions in Java code
- Automatically captures stack trace and device info

#### Native Crash
- SIGSEGV (Segmentation Fault)
- SIGABRT (Abort Signal)
- SIGBUS (Bus Error)
- Requires NDK library

#### ANR
- Main thread blocked for 5+ seconds
- Captures main thread stack trace

## Advanced Usage

### Custom Crash Handler

```java
CrashMonitor.init(this)
    .setCrashCallback(crashInfo -> {
        switch (crashInfo.getType()) {
            case JAVA_CRASH:
                // Handle Java crash
                uploadJavaCrash(crashInfo);
                break;
            case NATIVE_CRASH:
                // Handle native crash
                uploadNativeCrash(crashInfo);
                break;
            case ANR:
                // Handle ANR
                uploadAnrReport(crashInfo);
                break;
        }
    })
    .start();
```

### Access Crash Logs

```java
// Get all crash log files
File[] crashLogs = CrashStorage.getCrashLogs(context);

// Clear all crash logs
CrashStorage.clearCrashLogs(context);
```

## Next Steps

- Read [Configuration Guide](configuration.md) for advanced options
- Learn about [Native Crash Symbolication](symbolication.md)
- Check [Best Practices](best-practices.md) for production usage
