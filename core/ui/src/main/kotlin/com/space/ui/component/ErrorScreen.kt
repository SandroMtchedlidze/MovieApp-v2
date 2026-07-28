package com.space.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.space.core.ui.R
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import com.space.ui.theme.TextSizing

@Composable
fun ErrorScreen(
    title: String,
    description: String,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Sizing.size64),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.error),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(Sizing.size28))

        Text(
            text = title,
            style = typography.textMedium,
            color = colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.spacing22))

        Text(
            text = description,
            style = typography.titleMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Sizing.size110))

        ButtonRefresh(
            text = stringResource(R.string.refresh),
            iconRes = R.drawable.retry,
            onClick = onRefreshClick
        )
    }
}

@Composable
fun ButtonRefresh(
    text: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .width(Sizing.size134)
            .height(Sizing.size44)
            .clip(Radius.radius16)
            .background(colors.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = TextSizing.size16
                ),
                color = colors.onPrimary
            )

            Spacer(modifier = Modifier.width(Spacing.spacing4))

            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = colors.onPrimary
            )
        }
    }
}