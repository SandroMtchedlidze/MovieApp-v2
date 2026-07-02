package com.space.data.remote.di

import com.space.data.remote.mapper.MovieMapper
import com.space.data.remote.repository.FilterRepositoryImpl
import com.space.data.remote.repository.GenreRepositoryImpl
import com.space.data.remote.repository.MovieRepositoryImpl
import com.space.data.remote.repository.SearchMoviesRepositoryImpl
import com.space.domain.repository.FilterRepository
import com.space.domain.repository.GenreRepository
import com.space.domain.repository.MovieRepository
import com.space.domain.repository.SearchMoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieMapper() }

    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get(),
            movieMapper = get()
        )
    }
    single<GenreRepository> {
        GenreRepositoryImpl(
            genreApi = get(),
            responseHandler = get()
        )
    }
    single<SearchMoviesRepository> {
        SearchMoviesRepositoryImpl(
            searchApi = get(),
            movieMapper = get()
        )
    }
    single<FilterRepository> {
        FilterRepositoryImpl(
            discoverApi = get(),
            movieMapper = get()
        )
    }
}