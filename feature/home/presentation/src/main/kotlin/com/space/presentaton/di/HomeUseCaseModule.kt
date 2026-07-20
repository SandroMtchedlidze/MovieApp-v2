package com.space.presentaton.di

import com.space.domain.usecase.FilterUseCase
import com.space.domain.usecase.GetGenresUseCase
import com.space.domain.usecase.GetMoviesUseCase
import com.space.domain.usecase.GetPopularMoviesUseCase
import com.space.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

val homeUseCaseModule = module {
    single {
        GetPopularMoviesUseCase(repository = get())
    }
    single {
        GetGenresUseCase(repository = get())
    }
    single { SearchMoviesUseCase(repository = get()) }
    single { FilterUseCase(repository = get()) }
    single {
        GetMoviesUseCase(
            get(),
            get(),
            get()
        )
    }
}