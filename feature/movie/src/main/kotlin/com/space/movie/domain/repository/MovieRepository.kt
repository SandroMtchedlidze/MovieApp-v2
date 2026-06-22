package com.space.movie.domain.repository

import com.space.common.network.ApiResult
import com.space.movie.domain.model.Movie

interface MovieRepository {
    suspend fun getTopRatedMovies(page: Int): ApiResult<List<Movie>>
    suspend fun getGenres() : ApiResult<Map<Int,String>>
}