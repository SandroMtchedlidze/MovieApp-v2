package com.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Suppress("UNCHECKED_CAST")
class Navigator(
    val backStack: NavBackStack<NavKey>,
    private val timeProvider: () -> Long = System::currentTimeMillis
) {
    private val popResultCallBacks = mutableMapOf<NavKey, (PopResult) -> Unit>()
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
        if (!canNavigate()) return
        backStack.add(key)
    }

    fun <Result : PopResult> push(key: NavKey, onPopResult: ((Result) -> Unit)? = null) {
        if (!canNavigate()) return
        if (onPopResult != null) {
            popResultCallBacks[key] = onPopResult as (PopResult) -> Unit
        }
        backStack.add(key)
    }

    fun pop(popResult: PopResult? = null) {
        if (!canNavigate()) return
        val removed = backStack.removeLastOrNull()
        if (removed != null && popResult != null) {
            popResultCallBacks[removed]?.let {
                it.invoke(popResult)
                popResultCallBacks.remove(removed)
            }
        }
    }

    fun replaceLast(newKey: NavKey) {
        if (!canNavigate()) return
        backStack[backStack.lastIndex] = newKey
    }

    fun resetTo(key: NavKey) {
        if (!canNavigate()) return
        if (backStack.size == 1 && backStack.first() == key) return
        backStack.clear()
        backStack.add(key)
    }
}

@Composable
fun rememberNavigator(initialKey: NavKey): Navigator {
    val backStack = rememberNavBackStack(initialKey)
    return remember { Navigator(backStack) }
}