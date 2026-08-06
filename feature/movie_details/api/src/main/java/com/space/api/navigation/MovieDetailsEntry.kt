package com.space.api.navigation

import com.space.navigation.FeatureNavigationKey
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int) : FeatureNavigationKey