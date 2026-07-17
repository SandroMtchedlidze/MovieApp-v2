package com.space.data.remote.di

import com.space.data.remote.api.DiscoverApi
import com.space.data.remote.api.GenreApi
import com.space.data.remote.api.MovieApi
import com.space.data.remote.api.SearchApi
import org.koin.dsl.module
import retrofit2.Retrofit

val movieFeatureApiModule = module {
    single<MovieApi> {
        get<Retrofit>().create(MovieApi::class.java)
    }
    single<GenreApi> {
        get<Retrofit>().create(GenreApi::class.java)
    }
    single<SearchApi> {
        get<Retrofit>().create(SearchApi::class.java)
    }
    single<DiscoverApi> {
        get<Retrofit>().create(DiscoverApi::class.java)
    }
}