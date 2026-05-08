---
phase: 1
title: Baseline and Information Architecture
status: completed
priority: P1
effort: 0.5d
dependencies: []
---

# Phase 1: Baseline and Information Architecture

## Context Links

- [Brainstorm report](../reports/260508-2122-android-fundamentals-quest-brainstorm.md)
- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/catalog/CatalogEntry.kt`
- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/catalog/CatalogScreen.kt`
- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/navigation/Destinations.kt`
- `app/src/main/java/com/creative/androidfundamentalsbydantech/ui/navigation/AppNavHost.kt`

## Overview

Establish the new information architecture before building screens. The goal is to turn the old catalog into a secondary Lab Mode and define the main learner journey.

## Key Insights

- Current app is broad but fragmented: demos are useful, journey is weak.
- `Catalog.entries` already has enough topic coverage for a lab index.
- `ComingSoon` entries should not appear in the main path.
- Keep existing demo routes stable to reduce regression risk.

## Requirements

- Functional: define Quest home, Learning Path, Lesson, and Lab Mode destinations.
- Functional: map existing live demos into lab entries.
- Functional: choose 3 MVP lessons: Compose State, ViewModel/UDF, Room persistence.
- Non-functional: no multi-module split, no backend, no new permissions.
- Non-functional: keep Kotlin files under 200 lines when touched or newly created.

## Architecture

Main flow:

```text
QuestHome -> LearningPath -> LessonDetail
          -> LabMode -> existing demo screens
```

The catalog remains useful but loses responsibility for app identity.

## Related Code Files

- Modify: `ui/navigation/Destinations.kt`
- Modify: `ui/navigation/AppNavHost.kt`
- Modify: `ui/catalog/CatalogEntry.kt`
- Modify: `ui/catalog/CatalogScreen.kt`
- Read: `ui/common/DemoScaffold.kt`

## Implementation Steps

1. Run baseline compile before code changes: `./gradlew app:compileDebugKotlin`.
2. Inventory current live vs `ComingSoon` catalog entries.
3. Mark or split catalog entries into `main lesson`, `lab`, and `deferred` groups.
4. Define destination names for Quest Home, Learning Path, Lesson Detail, and Lab Mode.
5. Keep existing routes for live demos to avoid breaking launch-mode/activity behavior.
6. Document deferred topics in the plan or docs, not as primary UI cards.

## Todo List

- [x] Baseline compile recorded.
- [x] MVP lesson list finalized.
- [x] Lab-only entries identified.
- [x] Deferred topics removed from main path.
- [x] Navigation impact documented.

## Success Criteria

- [x] The implementation has a clear map from old catalog entries to new learning/lab roles.
- [x] No source file is renamed only for cosmetic reasons.
- [x] No `ComingSoon` item is required to complete the MVP journey.

## Risk Assessment

- Risk: breaking existing demos while moving navigation.
- Mitigation: keep old demo routes and add new routes around them.

## Security Considerations

- Do not add permissions in this phase.
- Do not expose new exported activities or receivers.

## Next Steps

Proceed to Phase 2 once the route/content model is agreed and baseline compile status is known.
