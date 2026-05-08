# Code Standards

## Core Principles

- Keep implementation simple: YAGNI, KISS, DRY.
- Prefer existing Compose, Hilt, DataStore, Room, and Navigation patterns.
- Keep source files under 200 lines when practical.
- Update existing files directly; do not create duplicate enhanced, new, or `V2` screens.

## Android Patterns

- Single Activity with Compose Navigation.
- Screen state belongs in ViewModels.
- Compose reads immutable state and sends events upward.
- Use `collectAsStateWithLifecycle` for StateFlow in screens.
- Use repositories for data boundaries when a ViewModel would otherwise know storage details.

## Learning Product Rules

- Primary flow must not show unfinished `ComingSoon` cards.
- Every main lesson needs concept, try, observe, challenge, and summary sections.
- Lab Mode may expose advanced demos, but the main journey should stay beginner-friendly.
- Preserve English Android terms inside Vietnamese-first explanations.

## Verification

- Run `./gradlew app:compileDebugKotlin` after implementation changes.
- Run `./gradlew app:testDebugUnitTest` for logic changes.
- Run `./gradlew app:lintDebug` before handoff or commit.
- Keep focused unit tests around lesson IDs, progress sanitization, and next-lesson selection when changing `ui/learning`.
- Expect the current AGP 8.4.2 / `compileSdk = 35` warning until the toolchain is upgraded.
