package com.space.ui.component

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.space.core.ui.R
import com.space.ui.theme.MovieAppTheme

/**
 * Custom navigation button.
 * when focused changes background color.
 * Unfocused state dark background color and white text color.
 * Focused state yellow background color and dark text color.
 *
 *
 * @param selected controls navigation and button content and background color.
 * @param label takes string to display button content name.
 * @param iconResId takes icon from resource to display.
 * @param modifier controls visual for button.
 * @param onClick controls navigation.
 */
@Composable
fun NavButton(
    selected: Boolean,
    label: String,
    @DrawableRes iconResId: Int,        //used DrawableRes annotation for safety.
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    //controls colors data class
    val colorGroup = if (selected) NavButtonColors.selected() else NavButtonColors.unselected()
    //surface connects everything together icon and text
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = Radius.radius8,
        color = colorGroup.backgroundColor,
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
                painter = painterResource(id = iconResId),
                contentDescription = label,
                tint = colorGroup.contentColor,
                modifier = Modifier.size(Sizing.size16)
            )
            Spacer(modifier = Modifier.width(Spacing.spacing10))
            Text(
                text = label,
                style = typography.bodyMedium,
                color = colorGroup.contentColor
            )
        }
    }
}
data class NavButtonColors(
    val backgroundColor: Color,
    val contentColor: Color
) {
    companion object {
        @Composable
        fun selected(): NavButtonColors {
            return NavButtonColors(
                backgroundColor = colors.primary,
                contentColor = colors.onPrimary
            )
        }
        @Composable
        fun unselected(): NavButtonColors {
            return NavButtonColors(
                backgroundColor = colors.surface,
                contentColor = colors.onSurface,
            )
        }
    }
}