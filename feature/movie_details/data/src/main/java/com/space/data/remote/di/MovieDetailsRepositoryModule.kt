package com.space.data.remote.di

import com.space.data.remote.mapper.MovieDetailsMapper
import com.space.data.remote.repository.MovieDetailsRepositoryImpl
import com.space.domain.repository.MovieDetailsRepository
import org.koin.dsl.module

val movieDetailsRepositoryModule = module {
    single { MovieDetailsMapper() }
    single<MovieDetailsRepository> {
        MovieDetailsRepositoryImpl(
            movieDetailsApi = get(),
            responseHandler = get(),
            movieMapper = get()
        )
    }
}