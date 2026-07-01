package com.space.data.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.space.data.BuildConfig
import com.space.data.remote.api.DiscoverApi
import com.space.data.remote.api.GenreApi
import com.space.data.remote.api.MovieApi
import com.space.data.remote.api.SearchApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json { ignoreUnknownKeys = true }

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_TOKEN}").build()
                chain.proceed(request)

            }.build()
    }
    single {
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .client(get()).addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }
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