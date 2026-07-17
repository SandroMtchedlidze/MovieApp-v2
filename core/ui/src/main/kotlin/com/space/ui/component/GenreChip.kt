package com.space.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.space.ui.theme.Color
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import com.space.ui.theme.TextSizing

@Composable
fun GenreChip(
    genre: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = genre,
        style = typography.titleMedium.copy(
            fontSize = TextSizing.size10,
            lineHeight = TextSizing.size14,
            letterSpacing = TextSizing.size1
        ),
        color = if (isSelected) colors.background else colors.onBackground,
        modifier = modifier
            .clip(Radius.radius22)
            .background(
                color = if (isSelected) colors.primary else Color.Transparent
            )
            .border(
                width = Sizing.size1,
                color = if (isSelected) colors.transparent else colors.onBackground,
                shape = Radius.radius22
            )
            .clickable { onClick() }
            .padding(
                top = Spacing.spacing4,
                start = Spacing.spacing12,
                end = Spacing.spacing12,
                bottom = Spacing.spacing4
            )
    )
}

@Composable
fun GenreRow(
    genres: Map<Int, String>,
    selectedGenreId: Int?,
    onGenreSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing8),
        contentPadding = PaddingValues(horizontal = Spacing.spacing16)
    ) {
        items(
            items = genres.entries.toList(),
            key = { it.key }
        ) { (id, name) ->
            GenreChip(
                genre = name,
                isSelected = id == selectedGenreId,
                onClick = { onGenreSelected(id) }
            )
        }
    }
}