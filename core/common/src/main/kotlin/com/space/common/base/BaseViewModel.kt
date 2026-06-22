package com.space.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.getValue

abstract class BaseViewModel<State, Event, SideEffect>(
    initialState: State
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    fun updateState(update: State.() -> State) {
        _state.update(update)
    }

    protected fun emitSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _sideEffect.send(sideEffect)
        }
    }

    abstract fun onEvent(event: Event)
}
