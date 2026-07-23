package com.space.presentation.di

import com.space.domain.usecase.GetMovieDetailsUseCase
import org.koin.dsl.module

val movieDetailsUseCaseModule = module {
    single { GetMovieDetailsUseCase(repository = get()) }
}