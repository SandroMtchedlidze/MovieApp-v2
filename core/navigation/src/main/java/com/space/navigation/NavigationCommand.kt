package com.space.navigation

interface NavigationCommand {
    fun execute(navigator: Navigator)

    data class Push(val key: FeatureNavigationKey) : NavigationCommand {
        override fun execute(navigator: Navigator) {
            navigator.push(key)
        }
    }

    data class Pop(val result: PopResult? = null) : NavigationCommand {
        override fun execute(navigator: Navigator) {
            navigator.pop()
        }
    }

    data class Replace(val key: FeatureNavigationKey) : NavigationCommand {
        override fun execute(navigator: Navigator) {
            navigator.replaceLast(key)
        }
    }
}