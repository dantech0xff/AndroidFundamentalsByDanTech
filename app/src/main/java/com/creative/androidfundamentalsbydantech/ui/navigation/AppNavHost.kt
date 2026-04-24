package com.creative.androidfundamentalsbydantech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.creative.androidfundamentalsbydantech.ui.animation.AnimationShowcaseScreen
import com.creative.androidfundamentalsbydantech.ui.canvas.CanvasPlaygroundScreen
import com.creative.androidfundamentalsbydantech.ui.catalog.CatalogScreen
import com.creative.androidfundamentalsbydantech.ui.common.ComingSoonScreen
import com.creative.androidfundamentalsbydantech.ui.compose.EffectsShowcaseScreen
import com.creative.androidfundamentalsbydantech.ui.compose.StateShowcaseScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.ChannelScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.CoroutinesScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.DeadlockSimulatorScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.FlowScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.LegacyThreadingScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.MemoryLeakScreen
import com.creative.androidfundamentalsbydantech.ui.concurrency.RaceConditionScreen
import com.creative.androidfundamentalsbydantech.ui.interop.AndroidViewInteropScreen
import com.creative.androidfundamentalsbydantech.ui.launchmodes.LaunchModesHubScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val back: () -> Unit = { navController.popBackStack() }

    NavHost(navController = navController, startDestination = Destination.Catalog.route) {
        composable(Destination.Catalog.route) {
            CatalogScreen(onOpenRoute = { route -> navController.navigate(route) })
        }
        composable(Destination.LaunchModes.route) { LaunchModesHubScreen(onBack = back) }

        composable(Destination.ComposeState.route) { StateShowcaseScreen(onBack = back) }
        composable(Destination.ComposeEffects.route) { EffectsShowcaseScreen(onBack = back) }
        composable(Destination.Animation.route) { AnimationShowcaseScreen(onBack = back) }
        composable(Destination.Canvas.route) { CanvasPlaygroundScreen(onBack = back) }
        composable(Destination.AndroidViewInterop.route) { AndroidViewInteropScreen(onBack = back) }

        composable(Destination.Coroutines.route) { CoroutinesScreen(onBack = back) }
        composable(Destination.Flow.route) { FlowScreen(onBack = back) }
        composable(Destination.Channel.route) { ChannelScreen(onBack = back) }
        composable(Destination.LegacyThreading.route) { LegacyThreadingScreen(onBack = back) }
        composable(Destination.Deadlock.route) { DeadlockSimulatorScreen(onBack = back) }
        composable(Destination.RaceCondition.route) { RaceConditionScreen(onBack = back) }
        composable(Destination.MemoryLeak.route) { MemoryLeakScreen(onBack = back) }

        composable(
            route = Destination.ComingSoon.ROUTE_TEMPLATE,
            arguments = listOf(navArgument(Destination.ComingSoon.ARG_SLUG) { type = NavType.StringType }),
        ) { entry ->
            val slug = entry.arguments?.getString(Destination.ComingSoon.ARG_SLUG).orEmpty()
            ComingSoonScreen(slug = slug, onBack = back)
        }
    }
}
