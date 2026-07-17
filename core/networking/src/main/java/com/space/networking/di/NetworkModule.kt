package com.space.networking.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.space.networking.network.ResponseHandler
import com.space.networking.network.ResponseHandlerImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json { ignoreUnknownKeys = true }

fun networkModule(tmdbToken: String) = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $tmdbToken")
                    .build()
                chain.proceed(request)
            }.build()
    }
    single {
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single<ResponseHandler> { ResponseHandlerImpl() }
}