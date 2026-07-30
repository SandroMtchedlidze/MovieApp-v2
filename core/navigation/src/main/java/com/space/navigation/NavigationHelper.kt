package com.space.navigation

data object FeatureNavigationHelper : NavigationHelper

interface NavigationHelper {

    fun push(
        key: FeatureNavigationKey,
    ) = NavigationCommand.Push(key)

    fun pop() = NavigationCommand.Pop
}