package com.space.presentation.base

import androidx.lifecycle.ViewModel
import com.space.navigation.FeatureNavigationHelper
import com.space.navigation.NavigationCommand
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, Event>(
    initialState: State
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    internal val navigationCommands = MutableSharedFlow<NavigationCommand>(
        extraBufferCapacity = 64
    )

    protected fun globalNavigator(navigation: FeatureNavigationHelper.() -> NavigationCommand) {
        navigationCommands.tryEmit(
            FeatureNavigationHelper.navigation()
        )
    }

    fun updateState(update: State.() -> State) {
        _state.update(update)
    }

    abstract fun onEvent(event: Event)
}