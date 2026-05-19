# Native Crash Symbolication

## Overview

When a native crash occurs, the stack trace contains memory addresses instead of function names. Symbolication converts these addresses into readable function names and line numbers.

## Prerequisites

- Debug symbols (`.so` files with debug info)
- `addr2line` tool (included in Android NDK)

## Symbolication Process

### 1. Find the Crash Address

From the crash log:
```
signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x0
    #00 pc 0x0000000000012345  /data/app/com.example.app/lib/arm64/libnative.so
```

The crash address is `0x0000000000012345`.

### 2. Get Debug Symbols

Debug symbols are usually in:
- `app/build/intermediates/merged_native_libs/debug/out/lib/arm64-v8a/`
- Or download from your build system

### 3. Run addr2line

```bash
# For arm64
aarch64-linux-android-addr2line -C -f -e libnative.so 0x12345

# For arm
arm-linux-androideabi-addr2line -C -f -e libnative.so 0x12345
```

### 4. Example Output

```
Java_com_example_app_NativeClass_nativeMethod
/path/to/source/native.cpp:42
```

## Automated Symbolication

### Using ndk-stack

```bash
# Pipe crash log through ndk-stack
adb logcat | ndk-stack -sym path/to/symbols/
```

### Using Breakpad

Breakpad can generate minidump files that can be symbolicated later:

```cpp
// In your native code
google_breakpad::MinidumpDescriptor descriptor(crash_dir);
google_breakpad::ExceptionHandler handler(descriptor, NULL, dumpCallback, NULL, true, -1);
```

## Best Practices

1. **Keep debug symbols** — Always keep `.so` files with debug info
2. **Version control** — Archive symbols with each release
3. **Server-side symbolication** — Implement symbolication on your crash reporting server
4. **Symbolicated logs** — Store both raw and symbolicated stack traces

## Common Issues

### "?? ??:0" in output

This means the debug symbols don't match the crash. Make sure you're using the exact same `.so` file that was on the device.

### Wrong function names

Ensure you're using the correct architecture (arm64 vs arm32).

### Missing line numbers

Compile with debug flags: `-g -O0`
