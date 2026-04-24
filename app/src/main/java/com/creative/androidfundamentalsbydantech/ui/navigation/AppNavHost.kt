package com.creative.androidfundamentalsbydantech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.creative.androidfundamentalsbydantech.ui.catalog.CatalogScreen
import com.creative.androidfundamentalsbydantech.ui.common.ComingSoonScreen
import com.creative.androidfundamentalsbydantech.ui.launchmodes.LaunchModesHubScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Destination.Catalog.route) {
        composable(Destination.Catalog.route) {
            CatalogScreen(
                onOpenRoute = { route -> navController.navigate(route) },
            )
        }
        composable(Destination.LaunchModes.route) {
            LaunchModesHubScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = Destination.ComingSoon.ROUTE_TEMPLATE,
            arguments = listOf(navArgument(Destination.ComingSoon.ARG_SLUG) { type = NavType.StringType }),
        ) { entry ->
            val slug = entry.arguments?.getString(Destination.ComingSoon.ARG_SLUG).orEmpty()
            ComingSoonScreen(
                slug = slug,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
