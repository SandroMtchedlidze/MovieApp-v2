package com.space.presentaton.di

import com.space.presentaton.mapper.MovieResponseToUiModel
import com.space.presentaton.mapper.MovieUiModelToDomain
import com.space.presentaton.vm.HomeVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeVmModule = module {
    single { MovieResponseToUiModel() }
    single { MovieUiModelToDomain() }
    viewModel<HomeVm> {
        HomeVm(
            getGenresUseCase = get(),
            provideHomeUseCase = get(),
            getAllFavouritesIdsUseCase = get(),
            toggleFavouriteUseCase = get(),
            movieUiMapper = get(),
            mapperToDomain = get(),
            connectivityObserver = get()
        )
    }
}