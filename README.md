# Android Fundamentals Quest

Android Fundamentals Quest is a single-module Android learning app for practicing modern Android fundamentals through a guided path, interactive lessons, and reusable labs.

The app was refactored from a loose demo catalog into a learner-first experience:

- **Quest Home** shows the next recommended lesson and progress.
- **Learning Path** groups lessons by module with stable lesson IDs.
- **Lesson screens** follow `Concept -> Try -> Observe -> Challenge -> Summary`.
- **Lab Mode** keeps the original demo catalog available without unfinished `ComingSoon` cards.
- **Progress persistence** uses Preferences DataStore.

## Tech Stack

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- Hilt
- Room
- Retrofit + OkHttp
- Paging 3
- WorkManager
- Preferences DataStore
- JUnit4

## Project Structure

```text
app/src/main/java/com/creative/androidfundamentalsbydantech/
├── data/
│   ├── local/          # Room notes database
│   ├── prefs/          # Existing settings DataStore
│   ├── progress/       # Quest progress DataStore + repository
│   ├── remote/         # GitHub Retrofit API
│   └── work/           # WorkManager worker
├── ui/
│   ├── learning/       # Quest Home, Learning Path, lessons, Lab Mode
│   ├── catalog/        # Reused demo catalog for labs
│   ├── compose/        # Compose state/effects labs
│   ├── data/           # Room, network, paging, settings, work demos
│   ├── arch/           # Architecture pattern demos
│   └── navigation/     # App routes and NavHost
└── activity/           # Main and launch-mode activities
```

## Quick Start

Open the project in Android Studio or use the Gradle wrapper:

```bash
./gradlew app:compileDebugKotlin
./gradlew app:testDebugUnitTest
./gradlew app:lintDebug
```

Install on a connected device:

```bash
./gradlew app:installDebug
adb shell am start -n com.creative.androidfundamentalsbydantech/.activity.MainActivity
```

## Learning Path

The current MVP path includes:

1. Compose State
2. ViewModel + UDF
3. Room Persistence
4. Compose Side-effects
5. Coroutines + Flow
6. Network + Paging
7. Background Work

The first three lessons are full lesson surfaces. Later lessons currently route to concise lesson shells with related labs.

## Verification

Latest verified checks:

- `./gradlew app:compileDebugKotlin`
- `./gradlew app:testDebugUnitTest`
- `./gradlew app:lintDebug`
- `./gradlew app:installDebug`
- App launch smoke test through `adb shell am start`

Known warning: Android Gradle Plugin `8.4.2` was tested up to `compileSdk = 34`, while this project compiles with SDK 35.

## Documentation

- [Codebase Summary](docs/codebase-summary.md)
- [System Architecture](docs/system-architecture.md)
- [Development Roadmap](docs/development-roadmap.md)
- [Project Changelog](docs/project-changelog.md)
- [Code Standards](docs/code-standards.md)

## Development Notes

- Keep the main journey beginner-friendly.
- Put advanced or comparison material in Lab Mode.
- Keep lesson IDs route-safe kebab-case slugs.
- Prefer small Compose files and ViewModel-owned state.
- Run compile, unit tests, and lint before committing.
