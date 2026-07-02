package com.space.data.remote.di

import com.space.data.remote.api.MovieDetailsApi
import org.koin.dsl.module
import retrofit2.Retrofit

val movieDetailsApiModule = module {
    single<MovieDetailsApi> {
        get<Retrofit>().create(MovieDetailsApi::class.java)
    }
}