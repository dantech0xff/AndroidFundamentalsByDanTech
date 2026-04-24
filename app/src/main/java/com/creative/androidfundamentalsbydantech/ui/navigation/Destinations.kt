package com.creative.androidfundamentalsbydantech.ui.navigation

sealed class Destination(val route: String, val title: String) {
    data object Catalog : Destination("catalog", "Android Skills Showcase")

    data object LaunchModes : Destination("launch-modes", "Activity Launch Modes")

    data class ComingSoon(val slug: String, val name: String) :
        Destination("coming-soon/$slug", name) {
        companion object {
            const val ROUTE_TEMPLATE = "coming-soon/{slug}"
            const val ARG_SLUG = "slug"
            fun routeFor(slug: String) = "coming-soon/$slug"
        }
    }
}
