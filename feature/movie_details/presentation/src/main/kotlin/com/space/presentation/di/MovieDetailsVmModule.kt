package com.space.presentation.di

import com.space.presentation.mapper.MovieDetailsToDomain
import com.space.presentation.mapper.MovieDetailsUiMapper
import com.space.presentation.vm.MovieDetailsVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val movieDetailsVmModule = module {
    single { MovieDetailsUiMapper() }
    single { MovieDetailsToDomain() }
    viewModel { params ->
        MovieDetailsVm(
            movieId = params.get(),
            getMovieDetailsUseCase = get(),
            toggleFavouriteUseCase = get(),
            getAllFavouritesIdsUseCase = get(),
            uiMapper = get(),
            domainMapper = get()
        )
    }
}