package com.space.movieapp

import android.app.Application
import com.space.data.remote.di.homeRepositoryModule
import com.space.data.remote.di.movieDetailsApiModule
import com.space.data.remote.di.movieDetailsRepositoryModule
import com.space.data.remote.di.movieFeatureApiModule
import com.space.networking.di.networkModule
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
                networkModule(BuildConfig.TMDB_TOKEN),
                homeRepositoryModule,
                useCaseModule,
                presentationModule,
                movieFeatureApiModule,
                movieDetailsApiModule,
                movieDetailsRepositoryModule
            )
        }
    }
}