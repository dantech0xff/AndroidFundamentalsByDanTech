---
title: Android Fundamentals Quest Refactor
description: >-
  Refactor the demo catalog into a guided Android fundamentals learning app with
  lessons, labs, and persisted progress.
status: completed
priority: P2
branch: codex/review-current-changes-for-improvements
tags:
  - android
  - compose
  - learning-path
  - refactor
blockedBy: []
blocks: []
created: '2026-05-08T14:48:28.511Z'
createdBy: 'ck:plan'
source: skill
---

# Android Fundamentals Quest Refactor

## Overview

Refactor the app from `Android Skills Showcase` into **Android Fundamentals Quest**: a guided learning path for Android fundamentals learners. The MVP keeps the single app module, reuses existing demos as labs, converts three core lessons, and persists learning progress.

Decision locks:
- Product name: `Android Fundamentals Quest`
- Mini-app thread: `Android Learning Journal`
- Language: Vietnamese-first content, English Android terms preserved
- Progress persistence: Preferences DataStore first; Room remains for journal notes
- Architecture: single Activity, Compose Navigation, screen-level ViewModels, repositories for data boundaries

## Phases

| Phase | Name | Status |
|-------|------|--------|
| 1 | [Baseline and Information Architecture](./phase-01-baseline-and-information-architecture.md) | Completed |
| 2 | [Learning Domain and Lesson Scaffold](./phase-02-learning-domain-and-lesson-scaffold.md) | Completed |
| 3 | [Quest Home and Navigation Shell](./phase-03-quest-home-and-navigation-shell.md) | Completed |
| 4 | [Convert Core Lessons](./phase-04-convert-core-lessons.md) | Completed |
| 5 | [Progress Persistence and Data Boundaries](./phase-05-progress-persistence-and-data-boundaries.md) | Completed |
| 6 | [Testing Review and Documentation](./phase-06-testing-review-and-documentation.md) | Completed |

## Status

- Plan: completed
- Progress: 6/6 phases complete
- Verification: `compileDebugKotlin`, `testDebugUnitTest`, `lintDebug`, and `installDebug` passed; `adb start` had no `AndroidRuntime` crash; `ck plan status` reported 6/6 done.

## Dependencies

- Source report: [brainstorm report](../reports/260508-2122-android-fundamentals-quest-brainstorm.md)
- No unfinished overlapping implementation plan found in project scope.

## Architecture Summary

- Start destination becomes a Quest home screen.
- Learning path data defines modules, lessons, order, status, and lab links.
- Lesson screens share `Concept -> Try -> Observe -> Challenge -> Summary`.
- Existing raw demos remain reachable from Lab Mode.
- Progress uses DataStore with stable lesson IDs and immutable UI state.
- Domain/use cases are added only where they reduce ViewModel complexity.

## External References

- Android Basics with Compose: https://developer.android.com/courses/android-basics-compose/course
- Android architecture guide: https://developer.android.com/topic/architecture
- Android architecture recommendations: https://developer.android.com/topic/architecture/recommendations
- Compose state: https://developer.android.com/develop/ui/compose/state
- DataStore: https://developer.android.com/topic/libraries/architecture/datastore

## Success Criteria

- App opens to the Quest home, not the old catalog.
- Main path has no `ComingSoon` entries.
- Three MVP lessons are complete and interactive.
- Existing advanced demos are still accessible from Lab Mode.
- Lesson progress persists after app restart.
- `./gradlew app:compileDebugKotlin` and `./gradlew app:testDebugUnitTest` pass after implementation.

## Handoff

Plan closed. No further implementation handoff pending.
