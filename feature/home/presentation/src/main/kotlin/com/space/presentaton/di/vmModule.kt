package com.space.presentaton.di

import com.space.presentaton.vm.HomeVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<HomeVm> {
        HomeVm(
            getGenresUseCase = get(),
            getTopRatedMoviesUseCase = get()
        )
    }
}