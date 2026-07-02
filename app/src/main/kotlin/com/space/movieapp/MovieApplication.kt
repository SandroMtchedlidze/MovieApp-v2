package com.space.movieapp

import android.app.Application
import com.space.data.remote.di.networkModule
import com.space.data.remote.di.repositoryModule
import com.space.presentaton.di.presentationModule
import com.space.presentaton.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                presentationModule,
            )
        }
    }
}