# Development Roadmap

## Overview

Project direction changed from API demo catalog to **Android Fundamentals Quest**: guided Android learning path with labs and persisted progress.

## Current Phase

| Phase | Status | Notes |
|---|---|---|
| Catalog demo baseline | Complete | Existing demos preserved as Lab Mode |
| Quest MVP refactor | Complete | Home, path, lessons, progress, tests |
| Remaining lesson polish | Planned | Convert non-MVP lessons into richer challenges |
| Emulator QA and UI polish | Planned | Manual walkthrough, visual polish, accessibility pass |

## MVP Scope

- Quest Home as app start screen.
- Learning Path with stable lesson IDs.
- Lab Mode for existing demos.
- Three core lessons: Compose State, ViewModel/UDF, Room Persistence.
- DataStore-backed lesson progress.
- Unit tests for learning content and progress resolution.

## Success Metrics

- Learner can continue next lesson from first screen.
- No `ComingSoon` item appears in primary journey.
- Progress survives app restart.
- Existing demos stay reachable from Lab Mode.
- Compile and unit tests pass.

## Next Milestones

1. Run emulator smoke test for Quest Home -> Lesson -> Complete -> Restart.
2. Convert Side-effects, Flow, Network, and WorkManager into full lessons.
3. Add accessibility labels and content descriptions where needed.
4. Add Compose UI smoke tests when emulator workflow is stable.
