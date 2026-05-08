# Codebase Summary

Generated from a repomix snapshot on 2026-05-08.

## Overview

Android Fundamentals Quest is a single-module Android app written in Kotlin with Jetpack Compose, Navigation Compose, Hilt, Room, Retrofit, Paging, WorkManager, and Preferences DataStore.

The current product direction is a guided learning path for Android fundamentals learners, with the older demo catalog reused as a secondary Lab Mode.

## Main Areas

- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/learning/` contains Quest Home, Learning Path, lesson routing, the shared lesson scaffold, Lab Mode, and the progress-aware ViewModels.
- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/catalog/` preserves the legacy demo catalog and is reused by Lab Mode with unfinished `ComingSoon` entries hidden.
- `app/src/main/java/com/creative/androidfundamentalsbydantech/data/progress/` contains the Preferences DataStore wrapper, repository, and progress state model.
- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/*` contains the existing Compose demo screens for state, concurrency, data, architecture, system integration, and other labs.
- `app/src/test/java/com/creative/androidfundamentalsbydantech/ui/learning/` contains focused unit tests for learning content and progress resolution.

## Quest Flow

1. The app starts at `Destination.QuestHome`.
2. Quest Home surfaces the next lesson, completion progress, and entry points into Learning Path and Lab Mode.
3. Learning Path lists lessons grouped by module with stable lesson IDs.
4. The lesson route loads a lesson by ID, records the last opened lesson, and marks completion through the progress repository.
5. Lab Mode reuses the catalog entry list without unfinished `ComingSoon` cards.

## Persistence and State

- `LearningProgressDataStore` persists `completedLessonIds` and `lastLessonId` in Preferences DataStore.
- `LearningProgressRepository` provides the UI boundary for progress reads and writes.
- `LearningProgressResolver` sanitizes stored IDs against the current lesson list before the UI uses them.
- `QuestHomeViewModel` and `LessonProgressViewModel` expose immutable UI state to Compose screens.

## Tests

- `learning-content-test.kt` checks unique, route-safe lesson IDs and primary-path ordering.
- `learning-progress-resolver-test.kt` checks unknown ID cleanup and next-lesson selection.
- These tests keep the Quest flow stable when lesson order or persisted progress changes.

## Build Notes

- The build still carries the known AGP 8.4.2 and `compileSdk = 35` warning noted in the changelog.
- The repo targets SDK 34 and compiles against SDK 35.

## See Also

- [Development Roadmap](./development-roadmap.md)
- [Project Changelog](./project-changelog.md)
- [System Architecture](./system-architecture.md)
- [Code Standards](./code-standards.md)
