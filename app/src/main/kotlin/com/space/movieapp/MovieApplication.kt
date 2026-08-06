package com.space.movieapp

import android.app.Application
import com.space.database.di.databaseModule
import com.space.database.di.favouritesUseCaseModule
import com.space.di.homeFeatureDiModule
import com.space.di.movieDetailsDiModule
import com.space.movieapp.di.mainActivityVmModule
import com.space.networking.di.networkModule
import com.space.presentation.di.favouritesVmModule
import com.space.presentation.di.networkObserverModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(
                networkModule(BuildConfig.TMDB_TOKEN),
                homeFeatureDiModule,
                movieDetailsDiModule,
                databaseModule,
                favouritesUseCaseModule,
                favouritesVmModule,
                networkObserverModule,
                mainActivityVmModule
            )
        }
    }
}