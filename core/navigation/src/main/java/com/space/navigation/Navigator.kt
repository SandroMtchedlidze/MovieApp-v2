package com.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class Navigator(
    val backStack: NavBackStack<NavKey>,
) {
    fun push(key: NavKey) {
        if (backStack.contains(key)) {
            val first = backStack.removeAt(0)
            backStack.add(first)
        } else backStack.add(key)
    }

    fun pop() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }
}

@Composable
fun rememberNavigator(initialKey: NavKey): Navigator {
    val backStack = rememberNavBackStack(initialKey)
    return remember { Navigator(backStack) }
}