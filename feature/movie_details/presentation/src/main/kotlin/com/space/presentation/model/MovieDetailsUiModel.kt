package com.space.presentation.model

data class MovieDetailsUiModel(
    val posterUrl: String?,
    val title: String,
    val ratingText: String,
    val genreText: String,
    val runtimeText: String,
    val yearText: String,
    val overviewText: String
)
