package com.space.presentation.network_observer

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Boolean>
}