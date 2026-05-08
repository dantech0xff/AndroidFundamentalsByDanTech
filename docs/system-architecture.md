# System Architecture

## Overview

The app is a single-module Android app using Kotlin, Jetpack Compose, Navigation Compose, Hilt, Room, Retrofit, Paging, WorkManager, and DataStore.

## Runtime Flow

```text
MainActivity
  -> AppNavHost
  -> QuestHome / LearningPath / LessonRoute / LabMode
```

## Learning Layer

- `LearningContent` defines stable lesson IDs, modules, and local lesson text.
- `QuestHomeViewModel` combines static content with persisted progress.
- `LessonProgressViewModel` records last opened lesson and completed lessons.
- The shared lesson scaffold renders the lesson structure.

## Data Layer

```text
Preferences DataStore -> LearningProgressDataStore -> LearningProgressRepository
Room -> NoteDao -> RoomViewModel
Retrofit/Paging/WorkManager -> existing lab demos
```

Use DataStore for small key-value progress state. Use Room for structured journal notes and persistence lessons.

## Navigation

- Main journey starts at `Destination.QuestHome`.
- Lesson detail route uses stable lesson ID: `lesson/{lessonId}`.
- Lab Mode keeps existing demo routes intact.
- `ComingSoon` routes remain available for old catalog compatibility but are not part of the primary journey.

## Boundaries

- UI screens do not edit Preferences directly.
- Learning content is static local code for MVP.
- No backend, account system, or multi-module split in the MVP.
