package com.space.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.space.ui.theme.MovieAppTheme
import com.space.ui.theme.Radius
import com.space.ui.theme.Spacing


@Composable
fun NavButton(
    selected: Boolean,
    label: String,
    iconPainter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) {
        MovieAppTheme.colors.primary
    } else {
        MovieAppTheme.colors.surface
    }
    val borderStroke = if (selected) {
        null
    } else {
        BorderStroke(1.dp, MovieAppTheme.colors.border)
    }

    val contentColor = if (selected) {
        MovieAppTheme.colors.onPrimary
    } else {
        MovieAppTheme.colors.onSurface
    }

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = Radius.radius8,
        color = backgroundColor,
        border = borderStroke
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = Spacing.spacing10,
                horizontal = Spacing.spacing42
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(Spacing.spacing10))



            Text(
                text = label,
                style = MovieAppTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }

}