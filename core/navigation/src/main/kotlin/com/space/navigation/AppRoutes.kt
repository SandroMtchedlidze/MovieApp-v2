package com.space.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

//@Serializable
//data object MovieRoute : NavKey

@Serializable
data class DetailsRoute(val movieId: Int) : NavKey

@Serializable
data object FavouritesRoute : NavKey

//featurebs gavuwero routebi
//navigacia gavitano core dan.