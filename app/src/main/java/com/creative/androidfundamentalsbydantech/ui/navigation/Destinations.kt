package com.creative.androidfundamentalsbydantech.ui.navigation

sealed class Destination(val route: String, val title: String) {
    data object Catalog : Destination("catalog", "Android Skills Showcase")

    data object LaunchModes : Destination("launch-modes", "Activity Launch Modes")

    // Phase 2 - UI & Compose
    data object ComposeState : Destination("compose-state", "State & Remembering")
    data object ComposeEffects : Destination("compose-effects", "Side-Effects")
    data object Animation : Destination("compose-animation", "Animation")
    data object Canvas : Destination("compose-canvas", "Canvas Playground")
    data object AndroidViewInterop : Destination("interop-android-view", "AndroidView Interop")

    // Phase 2 - Concurrency
    data object Coroutines : Destination("conc-coroutines", "Coroutines")
    data object Flow : Destination("conc-flow", "Flow / StateFlow / SharedFlow")
    data object Channel : Destination("conc-channel", "Channel")
    data object LegacyThreading : Destination("conc-legacy", "Legacy Threading")
    data object Deadlock : Destination("conc-deadlock", "Deadlock Simulator")
    data object RaceCondition : Destination("conc-race", "Race Condition")
    data object MemoryLeak : Destination("conc-leak", "Memory Leak (LeakCanary)")

    data class ComingSoon(val slug: String, val name: String) :
        Destination("coming-soon/$slug", name) {
        companion object {
            const val ROUTE_TEMPLATE = "coming-soon/{slug}"
            const val ARG_SLUG = "slug"
            fun routeFor(slug: String) = "coming-soon/$slug"
        }
    }
}
