package com.space.data.remote.di

import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.repository.MovieRepositoryImpl
import com.space.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieMapper() }

    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get(),
            genreApi = get(),
            movieMapper = get()
        )
    }
}