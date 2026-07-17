package com.space.presentaton.di

import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetMoviesUseCase(repository = get())
    }
    factory {
        GetGenresUseCase(repository = get())
    }
}