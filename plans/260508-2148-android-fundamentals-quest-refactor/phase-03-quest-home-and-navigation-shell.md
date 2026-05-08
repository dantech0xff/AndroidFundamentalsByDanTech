---
phase: 3
title: Quest Home and Navigation Shell
status: completed
priority: P1
effort: 1d
dependencies:
  - 1
  - 2
---

# Phase 3: Quest Home and Navigation Shell

## Context Links

- `activity/MainActivity.kt`
- `ui/navigation/AppNavHost.kt`
- `ui/navigation/Destinations.kt`
- `ui/catalog/CatalogScreen.kt`
- `ui/catalog/CatalogEntry.kt`

## Overview

Replace the catalog-first experience with a Quest home and learning path shell. Existing demos remain accessible from Lab Mode.

## Key Insights

- Main screen must answer "what should I do next?"
- Lab Mode protects existing work without making it the primary learner flow.
- Avoid bottom navigation until there are stable top-level sections that justify it.

## Requirements

- Functional: app start destination is Quest Home.
- Functional: home shows next lesson, overall progress, and lab entry.
- Functional: learning path lists modules and lessons in order.
- Functional: lab mode lists existing live demos and optional deferred topics.
- Non-functional: compact, work-focused Material 3 UI; no marketing hero.

## Architecture

Navigation:

```text
Destination.QuestHome
Destination.LearningPath
Destination.LessonDetail(lessonId)
Destination.LabMode
existing demo destinations
```

For MVP, home can derive progress from static content until Phase 5 wires persistence.

## Related Code Files

- Create: `ui/learning/quest-home-screen.kt`
- Create: `ui/learning/learning-path-screen.kt`
- Create: `ui/learning/lab-mode-screen.kt`
- Modify: `ui/navigation/Destinations.kt`
- Modify: `ui/navigation/AppNavHost.kt`
- Modify: `ui/catalog/CatalogScreen.kt`
- Modify: `ui/catalog/CatalogEntry.kt`

## Implementation Steps

1. Add Quest Home composable using static content and placeholder progress values.
2. Add Learning Path composable grouped by module.
3. Add Lab Mode composable that reuses or wraps current catalog behavior.
4. Update `Destination` with new route objects.
5. Update `AppNavHost` start destination to Quest Home.
6. Wire lesson routes by stable lesson ID.
7. Keep old demo routes reachable from Lab Mode.
8. Compile after navigation changes.

## Todo List

- [x] Quest Home added.
- [x] Learning Path added.
- [x] Lab Mode added.
- [x] Start destination changed.
- [x] Existing live demos still reachable.
- [x] Compile command passes.

## Success Criteria

- [x] App no longer opens to `CatalogScreen`.
- [x] User can reach first MVP lesson in one tap from home.
- [x] User can reach existing lab demos in two taps or fewer.
- [x] No primary-flow `ComingSoon` cards are visible.

## Risk Assessment

- Risk: route mismatch or duplicate lesson IDs.
- Mitigation: route all lessons through the static learning content registry.

## Security Considerations

- No new deep links or exported components in this phase.
- Lab Mode should not surface risky system demos as production-like actions without explanation.

## Next Steps

Convert the first three lesson screens in Phase 4.
