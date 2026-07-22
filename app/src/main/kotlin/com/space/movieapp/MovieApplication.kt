package com.space.movieapp

import android.app.Application
import com.space.data.remote.di.homeRepositoryModule
import com.space.data.remote.di.movieDetailsApiModule
import com.space.data.remote.di.movieDetailsRepositoryModule
import com.space.data.remote.di.movieFeatureApiModule
import com.space.database.di.databaseModule
import com.space.database.di.favouritesUseCaseModule
import com.space.networking.di.networkModule
import com.space.presentation.di.movieDetailsUseCaseModule
import com.space.presentation.di.movieDetailsVmModule
import com.space.presentaton.di.homeUseCaseModule
import com.space.presentaton.di.homeVmModule
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
                homeUseCaseModule,
                homeVmModule,
                movieFeatureApiModule,
                movieDetailsApiModule,
                movieDetailsRepositoryModule,
                movieDetailsVmModule,
                movieDetailsUseCaseModule,
                databaseModule,
                favouritesUseCaseModule
            )
        }
    }
}