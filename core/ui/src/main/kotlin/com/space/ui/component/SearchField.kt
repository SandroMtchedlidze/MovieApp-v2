package com.space.ui.component


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.space.core_ui.R
import com.space.ui.theme.Color.Neutral05LightGrey
import com.space.ui.theme.Color.PureWhite
import com.space.ui.theme.MovieAppTheme
import com.space.ui.theme.Radius
import com.space.ui.theme.Spacing
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.Color

@Composable
fun SearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    onFilterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .background(
                    color = MovieAppTheme.colors.surface,
                    shape = Radius.Radius25
                )
                .onFocusChanged { isFocused = it.isFocused }
                .padding(horizontal = Spacing.spacing16),

            singleLine = true,
            textStyle = MovieAppTheme.typography.bodyMedium.copy(
                color = MovieAppTheme.colors.textSecondary

            ),
            cursorBrush = SolidColor(MovieAppTheme.colors.primary),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        tint = Neutral05LightGrey,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(Spacing.spacing8))

                    Box {
                        if (query.isEmpty()) {
                            Text(
                                text = "Search",
                                style = MovieAppTheme.typography.bodyMedium,
                                color = Neutral05LightGrey
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )

        Spacer(Modifier.width(8.dp))
        AnimatedVisibility(visible = isFocused) {
            Text(
                text = "Cancel",
                style = MovieAppTheme.typography.bodyMedium,
                color = PureWhite,
                modifier = Modifier
                    .padding(
                        start = Spacing.spacing10
                    )
                    .clickable {
                        onCancelClicked()
                        focusManager.clearFocus()
                    }
            )
        }
        AnimatedVisibility(visible = !isFocused) {
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()

            Icon(
                painter = painterResource(R.drawable.fillter),
                contentDescription = "filter option",
                tint = if (isPressed) Color.Black else MovieAppTheme.colors.primary,
                modifier = modifier
                    .size(36.dp)
                    .background(
                        color = if (isPressed) MovieAppTheme.colors.primary else Color.Black,
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onFilterClicked()
                    }
            )
        }
    }
}