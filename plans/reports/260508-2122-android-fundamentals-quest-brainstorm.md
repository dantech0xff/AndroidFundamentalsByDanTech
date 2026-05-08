---
type: brainstorm-report
created: 2026-05-08
status: approved-direction
project: android-fundamentals
---

# Android Fundamentals Quest Brainstorm

## Summary

Chosen direction: refactor the project from a loose API demo catalog into **Android Fundamentals Quest**.

Goal: make the app useful and attractive for Android fundamentals learners by giving them a guided learning path, interactive lessons, and one practical mini app that grows across modules.

No implementation approved yet. This report captures design direction only.

## Problem

Current app is technically broad but educationally weak.

- Many isolated demos: Compose, concurrency, Room, Retrofit, Paging, WorkManager, architecture.
- Main UX is a catalog, not a learning journey.
- Many `ComingSoon` entries reduce trust.
- Learner cannot tell what to study first, what they should understand, or what they built.
- Architecture demos give MVC/MVP/MVVM/MVI equal weight, which can confuse beginners.

The project needs a learning product, not more demo screens.

## Requirements

- Beginner-friendly Android fundamentals path.
- Keep modern Android as the main track: Kotlin, Compose, Navigation, ViewModel, UDF, repository/data layer.
- Preserve useful existing demos as lab/playground material.
- Remove or hide unfinished lessons from the primary flow.
- Build toward a real mini app, not only API fragments.
- Keep implementation simple: single app module first, no premature modularization.
- Keep files small and focused during future refactor.

## Evaluated Approaches

| Approach | Pros | Cons | Decision |
|---|---|---|---|
| Polish existing catalog | Fast, low risk, reuses everything | Still passive, still fragmented, weak retention | Reject |
| Build one production clone | Practical, portfolio-like | Too much concept mixing for beginners, easy to overbuild | Reject for now |
| Guided quest + mini app | Clear learning path, interactive, still reuses code | Requires navigation/content refactor | Choose |

## Recommended Solution

Create **Android Fundamentals Quest** with this primary flow:

1. **Home**
   - Current progress.
   - Next recommended lesson.
   - Continue button.
   - Small module overview.

2. **Learning Path**
   - Ordered modules.
   - Visible completion status.
   - Locked/unlocked optional, but not required for MVP.

3. **Lesson Screen**
   - `Concept`: short explanation.
   - `Try`: interactive demo.
   - `Observe`: what changed and why.
   - `Challenge`: small learner task.
   - `Summary`: key takeaways.

4. **Lab Mode**
   - Existing demos live here.
   - Advanced/appendix topics move here.
   - Catalog becomes secondary, not the app identity.

5. **Mini App Thread**
   - Suggested product: **Android Learning Journal** or **Dev Study Tracker**.
   - Each module adds one real feature.
   - Learner sees how fundamentals compose into an actual app.

## Suggested Module Path

| Order | Module | Existing Code to Reuse | Learner Outcome |
|---:|---|---|---|
| 1 | Compose state | `StateShowcaseScreen` | Understand state, saveable state, hoisting |
| 2 | Side effects | `EffectsShowcaseScreen` | Use effects without accidental recomposition bugs |
| 3 | Navigation | `AppNavHost`, `Destination` | Move between list/detail/settings screens |
| 4 | ViewModel + UDF | MVVM/MVI demos | Model UI state and events |
| 5 | Local data | Room/DataStore screens | Persist notes, settings, progress |
| 6 | Network data | Retrofit/Paging screens | Fetch/search remote Android/GitHub resources |
| 7 | Background work | WorkManager screen | Schedule reminders or sync |
| 8 | Testing | existing test skeletons | Test use cases, ViewModels, and UI basics |

## Architecture Direction

Keep architecture pragmatic:

- Single Activity + Compose Navigation.
- Feature folders for lessons and mini-app features.
- ViewModel holds UI state.
- Repository abstracts local/network data.
- Domain/use cases only when they remove duplication or make tests clearer.
- MVC/MVP/MVI become comparison lessons or lab appendix.

Do not introduce multi-module architecture in MVP. It adds ceremony before the educational UX proves value.

## MVP Scope

First refactor should deliver:

- New home screen replacing catalog as start destination.
- Learning path model with 6-8 ordered lessons.
- Reusable lesson scaffold.
- 3 fully converted lessons:
  - Compose State
  - ViewModel/UDF
  - Room persistence
- Lab entry point for existing raw demos.
- Progress saved with DataStore or Room.
- No dead `ComingSoon` cards in the primary flow.

## Out of Scope for MVP

- Account/login.
- Remote backend.
- Firebase.
- Gamification economy.
- Multi-module Gradle split.
- Full course content for every Android topic.
- Heavy animation/polish before the learning loop works.

## Risks

| Risk | Impact | Mitigation |
|---|---|---|
| Too much content | Slow refactor, shallow lessons | Convert only 3 lessons first |
| Existing demos remain fragmented | Learner still confused | Make lab secondary |
| Over-engineered architecture | Hard for learners to read | Single module, simple feature folders |
| Beginner cognitive overload | Drop-off | Use `Concept -> Try -> Observe -> Challenge -> Summary` |
| File size creep | Hard for LLM/code review | Extract lesson models/scaffold/components |

## Success Metrics

- Learner can identify the next lesson in under 5 seconds.
- No unfinished `ComingSoon` item in the main journey.
- Each MVP lesson has a visible interaction and a challenge.
- App compiles after refactor.
- At least one progress state persists across app restart.
- Existing advanced demos still accessible from Lab Mode.

## Validation

- Compile with `./gradlew app:compileDebugKotlin`.
- Run unit tests after adding progress/lesson state logic.
- Manual emulator walkthrough:
  - open app
  - continue lesson
  - complete challenge
  - restart app
  - confirm progress persists
  - open Lab Mode demo

## Next Steps

1. Create implementation plan with phases.
2. Phase 1: refactor information architecture only.
3. Phase 2: add lesson model and reusable scaffold.
4. Phase 3: convert first three lessons.
5. Phase 4: add progress persistence.
6. Phase 5: test, review, docs.

## Unresolved Questions

- Final product name: `Android Fundamentals Quest`, `Android Learning Lab`, or `Dev Study Tracker`.
- Main mini app domain: learning journal vs habit/study tracker.
- Language strategy: Vietnamese-first, English-first, or mixed.
- Whether progress should use DataStore first or Room first.
