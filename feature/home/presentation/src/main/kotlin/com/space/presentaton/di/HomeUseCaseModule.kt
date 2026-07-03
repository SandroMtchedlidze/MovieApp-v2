package com.space.presentaton.di

import com.space.domain.usecase.FilterUseCase
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetMoviesUseCase
import com.space.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

val homeUseCaseModule = module {
    factory {
        GetMoviesUseCase(repository = get())
    }
    factory {
        GetGenresUseCase(repository = get())
    }
    factory { SearchMoviesUseCase(repository = get()) }
    factory { FilterUseCase(repository = get()) }
}