package com.space.di.domain

import com.space.movie.domain.usecase.GetTopRatedMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetTopRatedMoviesUseCase(repository = get())
    }
}