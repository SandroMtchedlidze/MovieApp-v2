package com.space.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    onFilterClicked: () -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //search input text field
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .weight(1f)
                .height(Sizing.size36)
                .background(
                    color = colors.surface,
                    shape = Radius.Radius25
                )
                .onFocusChanged { isFocused = it.isFocused }
                .padding(horizontal = Spacing.spacing16),
            singleLine = true,
            textStyle = typography.bodyMedium.copy(
                color = colors.textSecondary
            ),
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
            }
        )
        Spacer(Modifier.width(Spacing.spacing8))
        //shows cancel button when search field is focused.
        if (isFocused) {
            Text(
                text = stringResource(R.string.cancel),
                style = typography.bodyMedium,
                color = PureWhite,
                modifier = Modifier
                    .padding(
                        start = Spacing.spacing10
                    )
                    .clickable {
                        focusManager.clearFocus()
                        onCancelClicked()
                    }
            )
        }
        //shows filter icon when search field is un focused.
        if (!isFocused) {
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            Icon(
                painter = painterResource(R.drawable.fillter),
                contentDescription = stringResource(R.string.filter_option),
                tint = if (isPressed) colors.pureBlack else colors.primary,
                modifier = modifier
                    .size(Sizing.size36)
                    .background(
                        color = if (isPressed) colors.primary else colors.pureBlack,
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