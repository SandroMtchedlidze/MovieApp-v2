package com.space.database.di

import com.space.database.network_observer.ConnectivityObserver
import com.space.database.network_observer.ConnectivityObserverImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkObserverModule = module {
    single<ConnectivityObserver> {
        ConnectivityObserverImpl(androidContext())
    }
}