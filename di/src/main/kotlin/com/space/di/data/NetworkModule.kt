package com.space.di.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.space.di.BuildConfig
import com.space.movie.data.remote.api.GenreApi
import com.space.movie.data.remote.api.MovieApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

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
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory("application/json".toMediaType())
            )
            .build()
    }
    single<MovieApi> {
        get<Retrofit>().create(MovieApi::class.java)
    }
    single<GenreApi> {
        get<Retrofit>().create(GenreApi::class.java)
    }
}