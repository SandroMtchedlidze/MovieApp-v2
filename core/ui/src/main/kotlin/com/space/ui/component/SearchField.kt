package com.space.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.space.core.ui.R
import com.space.ui.theme.Color.Neutral05LightGrey
import com.space.ui.theme.Color.PureWhite
import com.space.ui.theme.MovieAppTheme.colors
import com.space.ui.theme.MovieAppTheme.typography
import com.space.ui.theme.Radius
import com.space.ui.theme.Sizing
import com.space.ui.theme.Spacing

/**
 * Custom search field with cancel button and filter button at the end.
 * When focused filter icon disappears and cancel button appears.
 * When unfocused cancel button disappears and filter appears.
 *
 *
 * @param query Displays current text value.
 * @param onQueryChanged Callback triggered when text is changed.
 * @param onCancelClicked Callback triggered when cancel button is clicked while in focus.
 * @param onFilterClicked Callback triggered when filter icon is clicked while un focused.
 */
@Composable
fun SearchField(
    query: String,
    isFocused: Boolean,
    modifier: Modifier = Modifier,
    isFilterActive: Boolean = false,
    onQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onCancelClicked: () -> Unit,
    onFilterClicked: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing8)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(Sizing.size36)
                .focusable()
                .background(color = colors.surface, shape = Radius.Radius25)
                .padding(horizontal = Spacing.spacing16),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .onFocusChanged { onFocusChanged(it.isFocused) },
                value = query,
                onValueChange = onQueryChanged,
                singleLine = true,
                textStyle = typography.bodyMedium.copy(color = colors.textSecondary),
                cursorBrush = SolidColor(colors.pureWhite),
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            tint = Neutral05LightGrey,
                            modifier = Modifier.size(Sizing.size14),
                        )
                        Spacer(Modifier.width(Spacing.spacing8))
                        Box {
                            if (query.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search),
                                    style = typography.bodyMedium,
                                    color = Neutral05LightGrey
                                )
                            }
                            innerTextField()
                        }
                    }
                },
            )
        }

        if (query.isNotEmpty() || isFocused) {
            Text(
                text = stringResource(R.string.cancel),
                style = typography.bodyMedium,
                color = PureWhite,
                modifier = Modifier.clickable {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onCancelClicked()
                }
            )
        } else {
            val interactionSource = remember { MutableInteractionSource() }
            Icon(
                painter = painterResource(R.drawable.fillter),
                contentDescription = stringResource(R.string.filter_option),
                tint = if (isFilterActive) colors.pureBlack else colors.primary,
                modifier = Modifier
                    .size(Sizing.size36)
                    .background(
                        color = if (isFilterActive) colors.primary else colors.pureBlack,
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onFilterClicked() }
            )
        }
    }
}