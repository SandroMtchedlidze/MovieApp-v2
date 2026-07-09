package com.space.database.di

import androidx.room.Room
import com.space.database.database.MovieDatabase
import com.space.database.datasource.contract.FavouritesLocalDataSource
import com.space.database.datasource.implementation.FavouritesLocalDataSourceImpl
import com.space.database.mapper.MovieMapper
import com.space.database.repository.FavouriteRepositoryImpl
import com.space.domain.repository.FavouriteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }
    single { get<MovieDatabase>().favouriteMovieDao() }

    single { MovieMapper() }
    single<FavouritesLocalDataSource> {
        FavouritesLocalDataSourceImpl(favouriteMovieDao = get())
    }
    single<FavouriteRepository> {
        FavouriteRepositoryImpl(
            favouritesLocalDataSource = get(),
            mapper = get()
        )
    }
}