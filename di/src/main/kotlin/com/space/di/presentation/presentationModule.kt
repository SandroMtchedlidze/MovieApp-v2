package com.space.di.presentation

import com.space.movie.presentation.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MovieViewModel(
            getTopRatedMoviesUseCase = get(),
            getGenresUseCase = get()
        )
    }
}