@file:JvmName("LabModeScreenKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.compose.runtime.Composable
import com.creative.androidfundamentalsbydantech.ui.catalog.CatalogScreen

@Composable
fun LabModeScreen(
    onBack: () -> Unit,
    onOpenRoute: (String) -> Unit,
) {
    CatalogScreen(
        title = "Lab Mode",
        includeComingSoon = false,
        onBack = onBack,
        onOpenRoute = onOpenRoute,
    )
}
