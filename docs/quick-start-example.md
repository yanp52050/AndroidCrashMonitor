# 🚀 Quick Start Example

## Step 1: Add Dependency

```gradle
// app/build.gradle
dependencies {
    implementation 'com.yan.crashmonitor:crash-monitor:1.0.0'
}
```

## Step 2: Initialize in Application

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        CrashMonitor.init(this)
            .enableJavaCrash(true)
            .enableNativeCrash(true)
            .enableAnrMonitor(true)
            .setCrashCallback(this::handleCrash)
            .start();
    }
    
    private void handleCrash(CrashInfo crashInfo) {
        // Log the crash
        Log.e("Crash", crashInfo.toString());
        
        // Upload to server
        CrashUploader.upload(crashInfo);
        
        // Show notification to user
        CrashNotifier.show(this, crashInfo);
    }
}
```

## Step 3: Test Java Crash

```java
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Trigger a test crash
        findViewById(R.id.btn_crash).setOnClickListener(v -> {
            throw new RuntimeException("Test crash!");
        });
    }
}
```

## Step 4: Test ANR

```java
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Trigger a test ANR (block main thread)
        findViewById(R.id.btn_anr).setOnClickListener(v -> {
            try {
                Thread.sleep(10000); // Block for 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
```

## Step 5: View Crash Logs

```java
// Get all crash logs
File[] logs = CrashStorage.getCrashLogs(context);
for (File log : logs) {
    Log.d("CrashLog", "Log file: " + log.getName());
}

// Clear all logs
CrashStorage.clearCrashLogs(context);
```

## Expected Output

When a crash occurs, you'll see:

```
I/CrashMonitor: CrashMonitor started
E/JavaCrashHandler: Uncaught exception in thread main
    java.lang.RuntimeException: Test crash!
        at com.example.app.MainActivity.lambda$onCreate$0(MainActivity.java:25)
        ...
I/CrashStorage: Crash log saved: /data/data/com.example.app/files/crash_logs/JAVA_CRASH_20260519_143025.crash
```

## Next Steps

- Read [Configuration Guide](configuration.md) for more options
- Learn about [Native Crash Symbolication](symbolication.md)
- Check [Best Practices](best-practices.md) for production use
