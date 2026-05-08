# Project Changelog

## 2026-05-08

### Added

- Added Android Fundamentals Quest learning path.
- Added Quest Home, Learning Path, Lab Mode, and lesson routing.
- Added reusable lesson scaffold with concept, try, observe, challenge, and summary sections.
- Added DataStore-backed learning progress storage.
- Added focused unit tests for lesson content and progress resolution.

### Changed

- App name changed to Android Fundamentals Quest.
- Start destination changed from catalog to Quest Home.
- Existing catalog is now reused as Lab Mode and hides unfinished `ComingSoon` entries there.
- Compose State, MVVM, and Room screens now support lesson completion flow.

### Notes

- Existing `.idea` changes were present before this implementation and were not modified intentionally.
- Android Gradle Plugin still warns that AGP 8.4.2 was tested up to compileSdk 34 while project uses compileSdk 35.
