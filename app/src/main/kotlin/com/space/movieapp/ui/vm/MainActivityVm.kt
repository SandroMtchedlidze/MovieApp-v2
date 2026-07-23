package com.space.movieapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.database.network_observer.ConnectivityObserver
import com.space.movieapp.ui.contract.MainActivityState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivityVm(
    private val networkObserver: ConnectivityObserver
) : ViewModel() {
    private val _state: MutableStateFlow<MainActivityState> = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    private fun loadSplashScreen() {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY.milliseconds)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkObserver.observe().collectLatest { connected ->
                _state.update { it.copy(isOnline = connected) }
            }
        }
    }

    init {
        loadSplashScreen()
        observeNetwork()
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 4000L
    }
}