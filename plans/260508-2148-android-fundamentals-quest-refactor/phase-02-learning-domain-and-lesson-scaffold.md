---
phase: 2
title: Learning Domain and Lesson Scaffold
status: completed
priority: P1
effort: 1d
dependencies:
  - 1
---

# Phase 2: Learning Domain and Lesson Scaffold

## Context Links

- `ui/common/DemoScaffold.kt`
- `ui/compose/StateShowcaseScreen.kt`
- `ui/arch/mvvm/MvvmShowcaseScreen.kt`
- `ui/data/room/RoomShowcaseScreen.kt`

## Overview

Create the small learning domain model and reusable lesson scaffold used by the first lessons. This phase should add structure without rewriting demo logic yet.

## Key Insights

- The scaffold is the product backbone: every lesson should feel consistent.
- Keep the domain simple: stable IDs, display text, section content, route.
- New files may use kebab-case per project rule while Kotlin classes stay PascalCase.

## Requirements

- Functional: represent modules, lessons, steps, estimated duration, and lab links.
- Functional: render the lesson sequence `Concept -> Try -> Observe -> Challenge -> Summary`.
- Functional: expose reusable lesson completion action.
- Non-functional: avoid a large generic framework; no dynamic remote content.
- Non-functional: keep composables stateless where practical.

## Architecture

Suggested files:

- Create: `ui/learning/learning-content.kt`
- Create: `ui/learning/learning-models.kt`
- Create: `ui/learning/lesson-scaffold.kt`
- Create: `ui/learning/lesson-section.kt`

Core model shape:

```kotlin
data class LessonDefinition(
    val id: String,
    val title: String,
    val moduleTitle: String,
    val route: String,
    val labRoute: String?,
)
```

Keep body content in code for MVP. A database/content CMS is out of scope.

## Related Code Files

- Create: `ui/learning/learning-models.kt`
- Create: `ui/learning/learning-content.kt`
- Create: `ui/learning/lesson-scaffold.kt`
- Create: `ui/learning/lesson-section.kt`
- Modify: `ui/common/DemoScaffold.kt` only if a small shared affordance is needed.

## Implementation Steps

1. Add learning model data classes with stable, kebab-case lesson IDs.
2. Add static MVP content list with 6-8 ordered lessons.
3. Add reusable `LessonScaffold` with top app bar, progress indicator, section navigation, and completion button.
4. Add section composables for concept, observation, challenge, and summary text.
5. Keep "Try" slot as composable content so existing demos can be embedded.
6. Add previews only if they do not require Hilt/ViewModel.
7. Compile after this phase.

## Todo List

- [x] Lesson models added.
- [x] MVP learning content added.
- [x] Lesson scaffold added.
- [x] Existing common UI reused where sensible.
- [x] Compile command passes.

## Success Criteria

- [x] All MVP lessons have unique stable IDs.
- [x] Scaffold can host arbitrary interactive Compose content.
- [x] No lesson scaffold file exceeds 200 lines.
- [x] The scaffold does not depend on Room, Retrofit, or DataStore.

## Risk Assessment

- Risk: building a mini CMS.
- Mitigation: static content only; refactor later if content grows.

## Security Considerations

- Lesson content is local; no remote execution or HTML rendering.
- Do not store user-entered challenge answers in this phase.

## Next Steps

Use this scaffold in Phase 3 navigation and Phase 4 lesson conversion.
