package com.space.data.remote.repository

import com.space.data.remote.datasource.contract.MovieDetailsRemoteDataSource
import com.space.data.remote.mapper.MovieDetailsMapper
import com.space.domain.model.MovieDetailsResponse
import com.space.domain.repository.MovieDetailsRepository
import com.space.networking.network.ApiResult
import com.space.networking.network.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDetailsRepositoryImpl(
    private val movieMapper: MovieDetailsMapper,
    private val responseHandler: ResponseHandler,
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {

    override fun getMovieDetails(movieId: Int): Flow<ApiResult<MovieDetailsResponse>> {
        return responseHandler.apiCall { movieDetailsRemoteDataSource.getMovieDetails(movieId) }
            .map { result ->
                when (result) {
                    is ApiResult.Loading -> result
                    is ApiResult.Success ->
                        ApiResult.Success(movieMapper.mapToDomain(result.data))

                    is ApiResult.Error -> result
                }
            }
    }
}