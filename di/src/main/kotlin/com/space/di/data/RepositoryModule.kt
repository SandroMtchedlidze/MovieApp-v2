package com.space.di.data

import com.space.movie.data.remote.repository.MovieRepositoryImpl
import com.space.movie.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(api = get())
    }
}