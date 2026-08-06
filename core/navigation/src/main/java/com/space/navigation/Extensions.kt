package com.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalGlobalNavigator = staticCompositionLocalOf<Navigator?> { null }

@Composable
fun globalNavigator() = LocalGlobalNavigator.current

@Composable
fun requireGlobalNavigator(): Navigator {
    return globalNavigator() ?: throw IllegalStateException("Global navigator is not available")
}