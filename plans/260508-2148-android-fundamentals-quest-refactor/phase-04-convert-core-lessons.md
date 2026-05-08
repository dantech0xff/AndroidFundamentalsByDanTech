---
phase: 4
title: Convert Core Lessons
status: completed
priority: P1
effort: 1.5d
dependencies:
  - 2
  - 3
---

# Phase 4: Convert Core Lessons

## Context Links

- `ui/compose/StateShowcaseScreen.kt`
- `ui/arch/mvvm/MvvmShowcaseScreen.kt`
- `ui/arch/mvvm/SimpleMvvmViewModel.kt`
- `ui/arch/common/PatternScreen.kt`
- `ui/data/room/RoomShowcaseScreen.kt`
- `ui/data/room/RoomViewModel.kt`
- `data/local/NoteDao.kt`

## Overview

Convert three existing demos into complete MVP lessons: Compose State, ViewModel/UDF, and Room persistence. Update existing screens directly instead of creating duplicate "enhanced" versions.

## Key Insights

- Existing demos already contain useful interactions.
- The refactor is mostly framing, sequencing, and challenge design.
- Use `collectAsStateWithLifecycle` for screen-level StateFlow collection where applicable.

## Requirements

- Functional: each lesson has concept, try, observe, challenge, summary.
- Functional: each lesson remains interactive.
- Functional: each lesson can mark complete once Phase 5 persistence exists.
- Non-functional: preserve existing demo behavior unless it confuses the lesson.
- Non-functional: avoid large rewrites of Room or MVVM data flow.

## Architecture

Lesson conversion strategy:

- Compose State: wrap `remember`, `rememberSaveable`, `derivedStateOf`, state hoisting demos.
- ViewModel/UDF: reuse `SimpleMvvmViewModel` and `PatternScreen` concepts, position MVC/MVP/MVI as lab comparisons.
- Room: reuse notes CRUD as Android Learning Journal storage.

## Related Code Files

- Modify: `ui/compose/StateShowcaseScreen.kt`
- Modify: `ui/arch/mvvm/MvvmShowcaseScreen.kt`
- Modify: `ui/arch/mvvm/SimpleMvvmViewModel.kt` only if needed for clearer UI state.
- Modify: `ui/data/room/RoomShowcaseScreen.kt`
- Modify: `ui/data/room/RoomViewModel.kt` only if needed for challenge feedback.
- Modify: `ui/navigation/AppNavHost.kt`

## Implementation Steps

1. Convert `StateShowcaseScreen` to use `LessonScaffold`.
2. Add challenge: edit a learning journal title using hoisted state.
3. Convert MVVM lesson to emphasize state down/events up.
4. Add challenge: encode input, observe busy state, clear history.
5. Convert Room lesson into the first persistent journal interaction.
6. Add challenge: create, pin, delete a journal note and observe persistence.
7. Move MVC/MVP/MVI links to Lab Mode or architecture appendix.
8. Replace screen-level `collectAsState` with `collectAsStateWithLifecycle` where ViewModels expose `StateFlow`.
9. Compile after each converted lesson if changes are broad.

## Todo List

- [x] Compose State lesson converted.
- [x] ViewModel/UDF lesson converted.
- [x] Room persistence lesson converted.
- [x] MVC/MVP/MVI repositioned outside main path.
- [x] Lifecycle-aware collection used for converted StateFlow screens.
- [x] Compile command passes.

## Success Criteria

- [x] Three MVP lessons are complete enough for a learner to use without external context.
- [x] Existing demo interactions still work.
- [x] No duplicate screen file with `Enhanced`, `New`, or `V2` naming exists.
- [x] Converted screen files are kept under 200 lines or split during the phase.

## Risk Assessment

- Risk: lessons become text-heavy and boring.
- Mitigation: keep each lesson centered on one interaction and one challenge.

## Security Considerations

- Room lesson stores only local learner notes.
- Do not add network or account storage to journal data.

## Next Steps

Persist lesson completion and next-lesson state in Phase 5.
