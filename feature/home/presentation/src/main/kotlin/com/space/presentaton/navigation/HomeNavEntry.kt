package com.space.presentaton.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.space.api.navigation.HomeRoute
import com.space.presentaton.screen.HomeScreen

fun EntryProviderScope<NavKey>.homeEntry() {
    entry<HomeRoute> {
        HomeScreen()
    }
}