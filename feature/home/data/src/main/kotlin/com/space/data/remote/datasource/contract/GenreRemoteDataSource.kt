package com.space.data.remote.datasource.contract

import com.space.data.remote.dto.genre.GenreResponseDto
import retrofit2.Response

interface GenreRemoteDataSource {
    suspend fun getGenres(): Response<GenreResponseDto>
}