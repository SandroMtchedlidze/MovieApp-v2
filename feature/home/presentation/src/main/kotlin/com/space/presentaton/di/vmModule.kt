package com.space.presentaton.di

import com.space.presentaton.mapper.MovieResponseToUiModel
import com.space.presentaton.vm.HomeVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { MovieResponseToUiModel() }
    viewModel<HomeVm> {
        HomeVm(
            getGenresUseCase = get(),
            getMoviesUseCase = get(),
            searchMoviesUseCase = get(),
            movieUiMapper = get()
        )
    }
}