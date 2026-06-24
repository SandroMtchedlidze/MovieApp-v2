package com.space.presentaton.di

import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetTopRatedMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetTopRatedMoviesUseCase(repository = get())
    }
    factory {
        GetGenresUseCase(repository = get())
    }
}