package com.space.movieapp

import android.app.Application
import com.space.di.data.networkModule
import com.space.di.data.repositoryModule
import com.space.di.domain.useCaseModule
import com.space.di.presentation.presentationModule
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
                presentationModule
            )
        }
    }
}