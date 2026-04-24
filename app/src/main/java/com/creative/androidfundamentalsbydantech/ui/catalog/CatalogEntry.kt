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
        add(comingSoon("compose-state", "State & Side-Effects", "LaunchedEffect, DisposableEffect, produceState.", CatalogCategory.UiCompose))
        add(comingSoon("compose-animation", "Animation Showcase", "animate*AsState, AnimatedVisibility, transitions.", CatalogCategory.UiCompose))
        add(comingSoon("compose-canvas", "Canvas Playground", "Drawing paths, gradients, gesture paint.", CatalogCategory.UiCompose))
        add(comingSoon("interop-android-view", "AndroidView Interop", "Embed legacy View inside Compose.", CatalogCategory.UiCompose))

        // Architecture
        add(comingSoon("arch-mvc", "MVC", "Model–View–Controller", CatalogCategory.Architecture))
        add(comingSoon("arch-mvp", "MVP", "Model–View–Presenter", CatalogCategory.Architecture))
        add(comingSoon("arch-mvvm", "MVVM", "Model–View–ViewModel", CatalogCategory.Architecture))
        add(comingSoon("arch-mvi", "MVI", "Intent–State reducer", CatalogCategory.Architecture))
        add(comingSoon("arch-clean", "Clean Architecture", "Domain / Data / Presentation layering.", CatalogCategory.Architecture))

        // Concurrency
        add(comingSoon("conc-coroutines", "Coroutines", "Structured concurrency, scopes, cancellation.", CatalogCategory.Concurrency))
        add(comingSoon("conc-flow", "Flow / StateFlow / SharedFlow", "Cold vs hot streams, operators.", CatalogCategory.Concurrency))
        add(comingSoon("conc-channel", "Channel", "Rendezvous, buffered, actor, produce.", CatalogCategory.Concurrency))
        add(comingSoon("conc-legacy", "Legacy Threading", "Thread, Handler, HandlerThread, Executor.", CatalogCategory.Concurrency))
        add(comingSoon("conc-deadlock", "Deadlock Simulator", "Two threads, lock order inversion.", CatalogCategory.Concurrency))
        add(comingSoon("conc-race", "Race Condition", "Compare var, Atomic, Mutex, synchronized.", CatalogCategory.Concurrency))
        add(comingSoon("conc-leak", "Memory Leak (LeakCanary)", "Intentional leak, detect with LeakCanary.", CatalogCategory.Concurrency))

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
        add(comingSoon("data-room", "Room Database", "Entities, DAO with Flow, migration.", CatalogCategory.Data))
        add(comingSoon("data-retrofit", "Retrofit + OkHttp", "Search GitHub repos with states.", CatalogCategory.Data))
        add(comingSoon("data-paging", "Paging 3", "Infinite list with Paging + Compose.", CatalogCategory.Data))
        add(comingSoon("data-datastore", "DataStore (Prefs + Proto)", "Persist settings.", CatalogCategory.Data))
        add(comingSoon("data-work", "WorkManager", "One-time, periodic, constraints.", CatalogCategory.Data))

        // Files
        add(comingSoon("files-storage", "Storage APIs", "Internal, external, SAF, MediaStore.", CatalogCategory.Files))
        add(comingSoon("files-assets", "Assets & Raw", "Read bundled resources.", CatalogCategory.Files))

        // Testing
        add(comingSoon("test-unit", "Unit Tests", "ViewModels, use cases, repos.", CatalogCategory.Testing))
        add(comingSoon("test-compose", "Compose UI Tests", "createAndroidComposeRule.", CatalogCategory.Testing))
    }

    fun groupedByCategory(): Map<CatalogCategory, List<CatalogEntry>> =
        entries.groupBy { it.category }
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
