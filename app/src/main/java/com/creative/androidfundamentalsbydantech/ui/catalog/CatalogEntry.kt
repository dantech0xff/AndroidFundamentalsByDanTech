package com.creative.androidfundamentalsbydantech.ui.catalog

import com.creative.androidfundamentalsbydantech.ui.navigation.Destination

enum class CatalogCategory(val label: String) {
    UiCompose("UI & Compose"),
    Architecture("Architecture Patterns"),
    Concurrency("Concurrency & Threading"),
    ActivityFragment("Activity & Fragment"),
    System("System Integration"),
    Data("Data Layer"),
    Files("Files & Storage"),
    Testing("Testing"),
}

data class CatalogEntry(
    val title: String,
    val subtitle: String,
    val category: CatalogCategory,
    val route: String,
)

object Catalog {
    val entries: List<CatalogEntry> = buildList {
        // UI & Compose
        add(comingSoon("compose-basics", "Compose Basics", "Remember, recomposition, Modifier, slots.", CatalogCategory.UiCompose))
        add(live("State & Remembering", "remember, rememberSaveable, derivedStateOf, CompositionLocal.", CatalogCategory.UiCompose, Destination.ComposeState))
        add(live("Side-Effects", "LaunchedEffect, DisposableEffect, SideEffect, produceState.", CatalogCategory.UiCompose, Destination.ComposeEffects))
        add(live("Animation Showcase", "animate*AsState, AnimatedVisibility, transitions, gestures.", CatalogCategory.UiCompose, Destination.Animation))
        add(live("Canvas Playground", "Paths, gradients, finger paint, particles.", CatalogCategory.UiCompose, Destination.Canvas))
        add(live("AndroidView Interop", "Embed legacy View inside Compose.", CatalogCategory.UiCompose, Destination.AndroidViewInterop))

        // Architecture
        add(live("MVC", "Controller giữ logic + threading; render ngược lại View.", CatalogCategory.Architecture, Destination.ArchMvc))
        add(live("MVP", "Contract-based: View + Presenter interfaces, attach/detach lifecycle.", CatalogCategory.Architecture, Destination.ArchMvp))
        add(live("MVVM", "ViewModel + StateFlow, View chỉ observe.", CatalogCategory.Architecture, Destination.ArchMvvm))
        add(live("MVI", "Unidirectional Intent → Event → Reducer → State.", CatalogCategory.Architecture, Destination.ArchMvi))
        add(live("Clean Architecture", "Domain / Data / Presentation + UseCases + Hilt @Binds.", CatalogCategory.Architecture, Destination.ArchClean))

        // Concurrency
        add(live("Coroutines", "launch/async, withContext, cancellation, SupervisorJob.", CatalogCategory.Concurrency, Destination.Coroutines))
        add(live("Flow / StateFlow / SharedFlow", "Cold vs hot, debounce / collectLatest / map.", CatalogCategory.Concurrency, Destination.Flow))
        add(live("Channel", "Rendezvous, buffered, produce.", CatalogCategory.Concurrency, Destination.Channel))
        add(live("Legacy Threading", "Thread, Handler, HandlerThread, Executor.", CatalogCategory.Concurrency, Destination.LegacyThreading))
        add(live("Deadlock Simulator", "Two threads, lock order inversion.", CatalogCategory.Concurrency, Destination.Deadlock))
        add(live("Race Condition", "Compare var, Atomic, Mutex, synchronized.", CatalogCategory.Concurrency, Destination.RaceCondition))
        add(live("Memory Leak (LeakCanary)", "Intentional leak, detect with LeakCanary.", CatalogCategory.Concurrency, Destination.MemoryLeak))

        // Activity & Fragment
        add(
            CatalogEntry(
                title = "Activity Launch Modes",
                subtitle = "standard, singleTop, singleTask, singleInstance.",
                category = CatalogCategory.ActivityFragment,
                route = Destination.LaunchModes.route,
            )
        )
        add(comingSoon("activity-lifecycle", "Activity & App Lifecycle", "ProcessLifecycleOwner, config changes.", CatalogCategory.ActivityFragment))
        add(comingSoon("fragment-interop", "Fragment Interop (legacy)", "FragmentContainerView inside Compose.", CatalogCategory.ActivityFragment))

        // System
        add(comingSoon("sys-permissions", "Runtime Permissions", "Camera, Location, Notifications, SMS.", CatalogCategory.System))
        add(comingSoon("sys-services", "Services", "Background, Foreground, Bound.", CatalogCategory.System))
        add(comingSoon("sys-notifications", "Notification Styles", "BigText, BigPicture, Media, Progress.", CatalogCategory.System))
        add(comingSoon("sys-broadcast", "Broadcast Receiver", "Dynamic register, battery changes.", CatalogCategory.System))
        add(comingSoon("sys-intents", "Intents & Deep Links", "Explicit, implicit, navigation deep link.", CatalogCategory.System))
        add(comingSoon("sys-provider", "ContentProvider (expose)", "App-owned Notes provider.", CatalogCategory.System))

        // Data
        add(live("Room Database", "Entities, DAO with Flow, migration 1→2.", CatalogCategory.Data, Destination.Room))
        add(live("Retrofit + OkHttp", "Search GitHub with sealed UiState.", CatalogCategory.Data, Destination.Network))
        add(live("Paging 3", "Paged GitHub search + Compose LazyColumn.", CatalogCategory.Data, Destination.Paging))
        add(live("DataStore (Preferences)", "Persist theme + username as Flow.", CatalogCategory.Data, Destination.Settings))
        add(live("WorkManager", "@HiltWorker, one-time + periodic + progress.", CatalogCategory.Data, Destination.Work))

        // Files
        add(comingSoon("files-storage", "Storage APIs", "Internal, external, SAF, MediaStore.", CatalogCategory.Files))
        add(comingSoon("files-assets", "Assets & Raw", "Read bundled resources.", CatalogCategory.Files))

        // Testing
        add(comingSoon("test-unit", "Unit Tests", "ViewModels, use cases, repos.", CatalogCategory.Testing))
        add(comingSoon("test-compose", "Compose UI Tests", "createAndroidComposeRule.", CatalogCategory.Testing))
    }

    fun groupedByCategory(includeComingSoon: Boolean = true): Map<CatalogCategory, List<CatalogEntry>> =
        entries
            .filter { includeComingSoon || !it.route.startsWith("coming-soon/") }
            .groupBy { it.category }
}

private fun comingSoon(
    slug: String,
    title: String,
    subtitle: String,
    category: CatalogCategory,
): CatalogEntry = CatalogEntry(
    title = title,
    subtitle = subtitle,
    category = category,
    route = Destination.ComingSoon.routeFor(slug),
)

private fun live(
    title: String,
    subtitle: String,
    category: CatalogCategory,
    destination: Destination,
): CatalogEntry = CatalogEntry(
    title = title,
    subtitle = subtitle,
    category = category,
    route = destination.route,
)
