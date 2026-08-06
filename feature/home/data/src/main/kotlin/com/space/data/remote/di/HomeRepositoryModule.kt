package com.space.data.remote.di

import androidx.paging.PagingConfig
import com.space.data.remote.cache.GenreCache
import com.space.data.remote.datasource.contract.DiscoverRemoteDataSource
import com.space.data.remote.datasource.contract.GenreRemoteDataSource
import com.space.data.remote.datasource.contract.MovieRemoteDataSource
import com.space.data.remote.datasource.contract.SearchRemoteDataSource
import com.space.data.remote.datasource.implementation.DiscoverRemoteDataSourceImpl
import com.space.data.remote.datasource.implementation.GenreRemoteDataSourceImpl
import com.space.data.remote.datasource.implementation.MovieRemoteDataSourceImpl
import com.space.data.remote.datasource.implementation.SearchRemoteDataSourceImpl
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

val homeRepositoryModule = module {
    single { MovieMapper() }
    single { GenreCache() }
    single<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(
            movieApi = get()
        )
    }
    single<SearchRemoteDataSource> {
        SearchRemoteDataSourceImpl(
            searchApi = get()
        )
    }
    single<DiscoverRemoteDataSource> {
        DiscoverRemoteDataSourceImpl(
            discoverApi = get()
        )
    }
    single<GenreRemoteDataSource> {
        GenreRemoteDataSourceImpl(
            genreApi = get()
        )
    }
    single<MovieRepository> {
        MovieRepositoryImpl(
            remoteDataSource = get(),
            movieMapper = get(),
            responseHandler = get(),
            pagingConfig = get()
        )
    }
    single<GenreRepository> {
        GenreRepositoryImpl(
            genreRemoteDataSource = get(),
            genreCache = get(),
            responseHandler = get()
        )
    }
    single<SearchMoviesRepository> {
        SearchMoviesRepositoryImpl(
            searchRemoteDataSource = get(),
            movieMapper = get(),
            responseHandler = get(),
            pagingConfig = get()
        )
    }
    single<FilterRepository> {
        FilterRepositoryImpl(
            discoverRemoteDataSource = get(),
            movieMapper = get(),
            responseHandler = get(),
            pagingConfig = get()
        )
    }
    single {
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false
        )
    }
}