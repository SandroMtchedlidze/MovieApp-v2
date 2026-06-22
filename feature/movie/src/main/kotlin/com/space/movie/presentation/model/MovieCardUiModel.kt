package com.space.movie.presentation.model

data class MovieCardUiModel(
    val id: Int,
    val title: String,
    val posterUrl : String,
    val genre : String,
    val releaseDate : String,
    val isFavourite : Boolean
)