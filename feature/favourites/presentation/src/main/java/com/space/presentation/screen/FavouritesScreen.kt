package com.space.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FavouritesScreen(
    onBack: () -> Unit,
    onNavigateToDetails: (movieId: Int) -> Unit,
) {
    Text("Hello party people")
}