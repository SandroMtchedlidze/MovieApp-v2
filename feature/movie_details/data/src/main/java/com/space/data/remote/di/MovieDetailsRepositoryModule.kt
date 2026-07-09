package com.space.data.remote.di

import com.space.data.remote.datasource.contract.MovieDetailsRemoteDataSource
import com.space.data.remote.datasource.implementation.MovieDetailsRemoteDataSourceImpl
import com.space.data.remote.mapper.MovieDetailsMapper
import com.space.data.remote.repository.MovieDetailsRepositoryImpl
import com.space.domain.repository.MovieDetailsRepository
import org.koin.dsl.module

val movieDetailsRepositoryModule = module {
    single { MovieDetailsMapper() }

    single<MovieDetailsRemoteDataSource> {
        MovieDetailsRemoteDataSourceImpl(
            movieDetailsApi = get()
        )
    }
    single<MovieDetailsRepository> {
        MovieDetailsRepositoryImpl(
            movieDetailsRemoteDataSource = get(),
            responseHandler = get(),
            movieMapper = get()
        )
    }
}