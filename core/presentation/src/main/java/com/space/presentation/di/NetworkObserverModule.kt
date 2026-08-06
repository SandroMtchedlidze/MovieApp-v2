package com.space.presentation.di


import com.space.presentation.network_observer.ConnectivityObserver
import com.space.presentation.network_observer.ConnectivityObserverImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkObserverModule = module {
    single<ConnectivityObserver> {
        ConnectivityObserverImpl(androidContext())
    }
}