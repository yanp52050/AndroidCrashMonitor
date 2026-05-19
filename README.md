# 🔥 AndroidCrashMonitor

<p align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/NDK-00599C?style=for-the-badge&logo=cplusplus&logoColor=white" alt="NDK"/>
  <img src="https://img.shields.io/github/stars/yanp52050/AndroidCrashMonitor?style=for-the-badge&logo=github" alt="Stars"/>
  <img src="https://img.shields.io/github/license/yanp52050/AndroidCrashMonitor?style=for-the-badge" alt="License"/>
</p>

> 🛡️ Lightweight Android stability monitoring SDK — Catch Java Crashes, Native Crashes, and ANRs with minimal performance overhead.

## ✨ Features

- **Java Crash Capture** — Global uncaught exception handler
- **Native Crash Capture** — Signal-based crash detection (SIGSEGV, SIGABRT, etc.)
- **ANR Detection** — Main thread watchdog with stack trace dump
- **Device Info Collection** — Automatic device/app state snapshot
- **Crash Symbolication** — Native crash addr2line support
- **Log Persistence** — Local crash log storage with rotation
- **Upload Ready** — HTTP callback interface for crash reporting

## 📦 Installation

```gradle
dependencies {
    implementation 'com.yan.crashmonitor:crash-monitor:1.0.0'
}
```

## 🚀 Quick Start

```java
// In your Application class
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        CrashMonitor.init(this)
            .enableJavaCrash(true)      // Enable Java crash capture
            .enableNativeCrash(true)    // Enable native crash capture  
            .enableAnrMonitor(true)     // Enable ANR detection
            .setCrashCallback(crash -> {
                // Upload crash report
                uploadToServer(crash);
            })
            .start();
    }
}
```

## 📊 Crash Report Example

```json
{
  "type": "JAVA_CRASH",
  "timestamp": 1716067200000,
  "device": {
    "model": "Xiaomi 14",
    "os": "Android 14",
    "sdk": 34
  },
  "app": {
    "version": "1.0.0",
    "process": "com.example.app"
  },
  "crash": {
    "exception": "java.lang.NullPointerException",
    "message": "Attempt to invoke...",
    "stackTrace": "..."
  }
}
```

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Application Layer                      │
├─────────────────────────────────────────────────────────┤
│  CrashMonitor SDK                                        │
│  ├── JavaCrashHandler (UncaughtExceptionHandler)         │
│  ├── NativeCrashHandler (Signal Handler)                 │
│  ├── AnrWatchDog (Main Thread Monitor)                   │
│  ├── DeviceInfoCollector                                 │
│  └── CrashStorage (SQLite/Files)                         │
├─────────────────────────────────────────────────────────┤
│  Android NDK (C/C++)                                     │
│  ├── Breakpad Integration                                │
│  ├── Signal Handler (SIGSEGV, SIGABRT, SIGBUS)           │
│  └── Native Stack Unwinding                              │
└─────────────────────────────────────────────────────────┘
```

## 📖 Documentation

- [Getting Started](docs/getting-started.md)
- [Configuration Guide](docs/configuration.md)
- [Native Crash Symbolication](docs/symbolication.md)
- [ANR Detection Deep Dive](docs/anr-detection.md)
- [Best Practices](docs/best-practices.md)

## 🤝 Contributing

Contributions are welcome! Please read our [Contributing Guide](CONTRIBUTING.md) first.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [xCrash](https://github.com/nicai/xcrash) — Inspiration for native crash handling
- [ANR-WatchDog](https://github.com/nicai/anr-watchdog) — ANR detection approach
- [Breakpad](https://chromium.googlesource.com/breakpad/breakpad/) — Google's crash reporting library

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/yanp52050">Yan</a>
</p>
