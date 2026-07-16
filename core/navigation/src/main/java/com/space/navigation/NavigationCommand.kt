package com.space.navigation

interface NavigationCommand {
    fun execute(navigator: Navigator)

    data class Push(val key: FeatureNavigationKey) : NavigationCommand {
        override fun execute(navigator: Navigator) {
            navigator.push(key)
        }
    }

    data object Pop : NavigationCommand {
        override fun execute(navigator: Navigator) {
            navigator.pop()
        }
    }
}