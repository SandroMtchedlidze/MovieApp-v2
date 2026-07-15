package com.space.presentaton.contract

import com.space.navigation.NavigationCommand

sealed class HomeSideEffect {
    data class Navigate(val command: NavigationCommand) : HomeSideEffect()
}