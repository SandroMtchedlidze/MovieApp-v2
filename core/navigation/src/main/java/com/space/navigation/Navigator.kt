package com.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class Navigator(
    val backStack: NavBackStack<NavKey>,
    private val timeProvider: () -> Long = System::currentTimeMillis
) {
    private var lastNavigationTime = 0L

    private companion object {
        const val NAVIGATION_THROTTLE_MS = 500L
    }

    private fun canNavigate(): Boolean {
        val now = timeProvider()
        return if (now - lastNavigationTime > NAVIGATION_THROTTLE_MS) {
            lastNavigationTime = now
            true
        } else {
            false
        }
    }

    fun push(key: NavKey) {
        if (!canNavigate() || backStack.lastOrNull() == key) return
        if (backStack.contains(key)) {
            val first = backStack.removeAt(0)
            backStack.add(first)
        } else backStack.add(key)
    }

    fun pop() {
        if (!canNavigate()) return
        if (backStack.size > 1) backStack.removeLastOrNull()
    }
}

@Composable
fun rememberNavigator(initialKey: NavKey): Navigator {
    val backStack = rememberNavBackStack(initialKey)
    return remember { Navigator(backStack) }
}