
# 🥗 Calorie Tracker App

## 📱 Overview

**Calorie Tracker** is an Android application designed to help users monitor their daily calorie intake and burned calories through meals and exercise entries. The app allows users to log what they eat and how much they exercise, then calculates the net calorie balance to encourage healthier living.

This app was built using **Kotlin**, **Android Jetpack**, and **Room Database**, following modern Android development practices.

---

## ✨ Features

- 📊 Log daily meals and exercises
- 🔍 View summary of calorie intake and expenditure
- 🧠 Store user entries in a local Room database
- 🖼️ Intuitive UI with data-binding
- 🧹 ProGuard-ready for release builds

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (Jetpack Persistence Library)
- **UI**: Android XML layouts with Data Binding
- **Build System**: Gradle Kotlin DSL (`.kts`)

---

## 📁 Project Structure

```
CalorieTracker/
├── app/
│   ├── build.gradle.kts         # App-level Gradle build script
│   ├── proguard-rules.pro       # ProGuard rules for release
│   ├── src/...                  # Main application source code
│   └── res/                     # UI resources (layouts, drawables, etc.)
├── build.gradle.kts             # Project-level Gradle script
├── settings.gradle.kts          # Project modules declaration
├── gradle.properties            # Gradle configuration properties
├── gradlew / gradlew.bat        # Gradle wrapper scripts
```

---

## 🚀 Getting Started

1. Clone the repository or extract the ZIP into your development directory.
2. Open the project with **Android Studio (Electric Eel or newer recommended)**.
3. Allow Gradle to sync and download dependencies.
4. Run the app on an emulator or physical device running **Android 7.0+**.

---

## 🧪 Testing

Unit and UI tests can be added under `app/src/test/` and `app/src/androidTest/`. The app structure supports testability via MVVM separation.

---

## 📄 License

This project is provided for academic use and is developed as part of coursework or personal experimentation. See `LICENSE` file for more information.
