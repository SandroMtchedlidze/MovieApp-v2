package com.space.presentation.di


import com.space.presentation.mapper.MovieUiModelToDomain
import com.space.presentation.vm.FavouritesVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favouritesVmModule = module {
    single { MovieUiModelToDomain() }
    viewModel<FavouritesVm> {
        FavouritesVm(
            getAllFavouritesUseCase = get(),
            toggleFavouriteUseCase = get(),
            mapper = get()
        )
    }
}