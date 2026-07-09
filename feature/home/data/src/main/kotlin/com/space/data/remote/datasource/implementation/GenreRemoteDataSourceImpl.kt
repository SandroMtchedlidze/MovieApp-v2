package com.space.data.remote.datasource.implementation

import com.space.data.remote.api.GenreApi
import com.space.data.remote.datasource.contract.GenreRemoteDataSource
import com.space.data.remote.dto.genre.GenreResponseDto
import retrofit2.Response

class GenreRemoteDataSourceImpl(
    private val genreApi: GenreApi
) : GenreRemoteDataSource {
    override suspend fun getGenres(): Response<GenreResponseDto> {
        return genreApi.getGenres()
    }
}