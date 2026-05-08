---
title: android-fundamentals-quest-refactor
date: 2026-05-08 22:28 +07
severity: medium
component: app navigation, lessons, progress persistence
status: resolved
---

# Android Fundamentals Quest Refactor

## Context
The app started as a loose Android demo catalog. That made it broad, but not teachable. The refactor turned it into Android Fundamentals Quest: Home drives the journey, Learning Path orders the curriculum, Lab Mode keeps the raw demos available, and a shared lesson scaffold now frames each lesson around concept, practice, and outcome.

## What Happened
We converted the core educational flow into a guided path with 7 local lessons, including Compose State, ViewModel/MVVM-UDF, and Room. Learning progress now persists through DataStore with a corruption handler, so a damaged store does not break resume behavior. Tests were added alongside the new lesson/state code, and the docs plus plan were synced with the new structure.

## The Brutal Truth
The old app was technically interesting and pedagogically weak. It asked beginners to browse instead of learn. That is a bad product decision, not a UI problem. The frustrating part is that the codebase already contained useful material; the mistake was leaving it flattened into a catalog and pretending that counted as a course.

## Technical Details
Verification passed cleanly:
- `./gradlew app:compileDebugKotlin`
- `./gradlew app:testDebugUnitTest`
- `./gradlew app:lintDebug`
- `./gradlew app:installDebug`
- adb launch smoke test with no `AndroidRuntime` crash

## What We Tried
We rejected two weaker options:
- Keep the catalog as the main entry point. It preserved breadth but left the experience fragmented.
- Leave architecture demos at equal weight. That kept the confusion intact for beginners.

## Review Findings Addressed
The refactor removed the catalog-first start, pushed unfinished content out of the primary journey, and made lesson progression explicit instead of implied. That was the real review feedback: the app needed an opinionated path, not more demo cards.

## Root Cause Analysis
The root cause was scope drift. We accumulated demos faster than we shaped them into a learning sequence, so the app optimized for coverage over comprehension.

## Lessons Learned
A course needs an opinionated order. Reusing old screens is not enough if the learner cannot tell what matters first.

## Residual Risks
AGP 8.4.2 has only been tested up to compileSdk 34, while this project uses compileSdk 35. Also, unrelated `.idea` changes remain in the worktree and should stay untouched.

## Next Steps
Keep monitoring the AGP/compileSdk gap during future dependency bumps, and treat the current lesson scaffold as the baseline for any new Android fundamentals content.
