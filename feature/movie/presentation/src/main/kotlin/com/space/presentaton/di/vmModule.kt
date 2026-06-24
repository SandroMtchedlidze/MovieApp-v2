package com.space.presentaton.di

import com.space.presentaton.vm.MovieVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<MovieVm> {
        MovieVm(
            getGenresUseCase = get(),
            getTopRatedMoviesUseCase = get()
        )
    }
}