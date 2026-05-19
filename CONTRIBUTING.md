# Contributing to AndroidCrashMonitor

Thank you for your interest in contributing! 🎉

## How to Contribute

### 1. Fork the Repository

```bash
# Fork on GitHub, then clone
git clone https://github.com/yanp52050/AndroidCrashMonitor.git
cd AndroidCrashMonitor
```

### 2. Create a Branch

```bash
git checkout -b feature/your-feature-name
```

### 3. Make Changes

- Follow the existing code style
- Add comments for complex logic
- Update documentation if needed

### 4. Test Your Changes

```bash
# Run tests
./gradlew test

# Build the project
./gradlew build
```

### 5. Commit and Push

```bash
git add .
git commit -m "Add: description of your changes"
git push origin feature/your-feature-name
```

### 6. Create a Pull Request

- Go to the original repository
- Click "New Pull Request"
- Fill in the PR template
- Wait for review

## Development Setup

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 21+
- NDK (for native code)

### Project Structure

```
AndroidCrashMonitor/
├── library/              # Main SDK code
│   └── src/main/
│       ├── java/         # Java source
│       └── native/       # C/C++ source
├── sample/               # Sample app
└── docs/                 # Documentation
```

## Code Style

- Use 4 spaces for indentation
- Add Javadoc for public methods
- Use meaningful variable names
- Keep methods short and focused

## Reporting Issues

When reporting issues, please include:

1. Android version
2. Device model
3. Steps to reproduce
4. Expected behavior
5. Actual behavior
6. Crash logs (if applicable)

## License

By contributing, you agree that your contributions will be licensed under the MIT License.
