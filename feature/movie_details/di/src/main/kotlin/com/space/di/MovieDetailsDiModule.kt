package com.space.di

import com.space.data.remote.di.movieDetailsApiModule
import com.space.data.remote.di.movieDetailsRepositoryModule
import com.space.presentation.di.movieDetailsUseCaseModule
import com.space.presentation.di.movieDetailsVmModule
import org.koin.dsl.module

val movieDetailsDiModule = module {
    includes(
        movieDetailsUseCaseModule,
        movieDetailsVmModule,
        movieDetailsApiModule,
        movieDetailsRepositoryModule
    )
}