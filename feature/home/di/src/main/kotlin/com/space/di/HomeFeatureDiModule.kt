package com.space.di

import com.space.data.remote.di.homeRepositoryModule
import com.space.data.remote.di.movieFeatureApiModule
import com.space.presentaton.di.homeUseCaseModule
import com.space.presentaton.di.homeVmModule
import org.koin.dsl.module

val homeFeatureDiModule = module {
    includes(
        homeVmModule,
        homeUseCaseModule,
        homeRepositoryModule,
        movieFeatureApiModule
    )
}