---
phase: 5
title: Progress Persistence and Data Boundaries
status: completed
priority: P1
effort: 1d
dependencies:
  - 2
  - 3
  - 4
---

# Phase 5: Progress Persistence and Data Boundaries

## Context Links

- `data/prefs/SettingsDataStore.kt`
- `data/local/AppDatabase.kt`
- `data/local/NoteDao.kt`
- `di/DataModule.kt`
- `ui/data/settings/SettingsViewModel.kt`

## Overview

Persist lesson progress and clean up data boundaries so UI screens do not talk directly to data sources. Use DataStore for progress because the data is small and key-value shaped.

## Key Insights

- DataStore is a better MVP fit than Room for completed lesson IDs and last lesson ID.
- Room remains useful for journal notes and persistence learning.
- Android architecture guidance recommends repositories even for one data source.

## Requirements

- Functional: persist completed lesson IDs.
- Functional: persist last active lesson ID.
- Functional: Quest Home uses real progress state.
- Functional: lessons can mark themselves complete.
- Non-functional: no destructive Room migration for progress.
- Non-functional: no new remote storage or account dependency.

## Architecture

Data flow:

```text
DataStore -> LearningProgressRepository -> QuestHomeViewModel / LessonViewModel -> Compose UI
```

Suggested files:

- Create: `data/progress/learning-progress-data-store.kt`
- Create: `data/progress/learning-progress-repository.kt`
- Create: `ui/learning/quest-home-view-model.kt`
- Create: `ui/learning/lesson-progress-view-model.kt`

Use `stringSetPreferencesKey` for completed lesson IDs and `stringPreferencesKey` for last lesson ID.

## Related Code Files

- Create: `data/progress/learning-progress-data-store.kt`
- Create: `data/progress/learning-progress-repository.kt`
- Create: `ui/learning/quest-home-view-model.kt`
- Create: `ui/learning/lesson-progress-view-model.kt`
- Modify: `ui/learning/quest-home-screen.kt`
- Modify: `ui/learning/lesson-scaffold.kt`
- Modify: `di/DataModule.kt` only if constructor injection is not enough.

## Implementation Steps

1. Add progress data model with completed IDs and current/next lesson resolution.
2. Add DataStore wrapper dedicated to learning progress.
3. Add repository exposing progress as `Flow<LearningProgress>`.
4. Add completion and last-lesson update functions.
5. Wire home screen to a ViewModel.
6. Wire lesson scaffold completion action to progress persistence.
7. Ensure invalid/deleted lesson IDs are ignored safely.
8. Compile and run targeted unit tests after wiring.

## Todo List

- [x] Progress data model added.
- [x] DataStore wrapper added.
- [x] Repository added.
- [x] Home progress is real.
- [x] Lesson completion persists.
- [x] Invalid lesson IDs handled.

## Success Criteria

- [x] Completed lesson remains completed after app restart.
- [x] Home recommends the first incomplete lesson.
- [x] Progress layer has unit-testable logic outside Compose.
- [x] No ViewModel directly edits `Preferences`.

## Risk Assessment

- Risk: DataStore parsing becomes ad hoc.
- Mitigation: use typed wrapper functions and `stringSetPreferencesKey`, not comma-separated strings.

## Security Considerations

- Progress contains no confidential data.
- Keep preferences private to app storage.
- Do not add backup exclusions unless future sensitive data is introduced.

## Next Steps

Finish with regression tests, review, and docs in Phase 6.
