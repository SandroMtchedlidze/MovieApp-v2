package com.space.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.space.ui.theme.Color.Black
import com.space.ui.theme.Color.Neutral01Black
import com.space.ui.theme.Color.Neutral02DarkestGrey
import com.space.ui.theme.Color.Neutral03DarkGrey
import com.space.ui.theme.Color.Neutral04Grey
import com.space.ui.theme.Color.Neutral05LightGrey
import com.space.ui.theme.Color.Neutral06LighterGrey
import com.space.ui.theme.Color.Neutral08Whisper
import com.space.ui.theme.Color.PureWhite
import com.space.ui.theme.Color.YellowPrimary

val colors: MovieAppColors
    @Composable
    get() = MovieAppTheme.colors

val typography: MovieAppTypography
    @Composable
    get() = MovieAppTheme.typography

@Immutable
data class MovieAppColors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val onPrimary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textHint: Color,
    val border: Color,
    val pureBlack: Color,
    val pureWhite: Color
)

@Immutable
data class MovieAppTypography(
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val bodyMedium: TextStyle
)

val LocalMovieAppColors = staticCompositionLocalOf {
    MovieAppColors(
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        pureBlack = Color.Unspecified,
        pureWhite = Color.Unspecified
    )
}
val LocalMovieAppTypography = staticCompositionLocalOf {
    MovieAppTypography(
        TextStyle.Default,
        TextStyle.Default,
        TextStyle.Default
    )
}

object MovieAppTheme {
    val colors: MovieAppColors @Composable get() = LocalMovieAppColors.current
    val typography: MovieAppTypography @Composable get() = LocalMovieAppTypography.current
}

@Composable
fun MovieAppTheme(content: @Composable () -> Unit) {
    val customColors = MovieAppColors(
        primary = YellowPrimary,
        background = Neutral01Black,
        surface = Neutral02DarkestGrey,
        onPrimary = Neutral01Black,
        onBackground = Neutral08Whisper,
        onSurface = Neutral08Whisper,
        textSecondary = Neutral05LightGrey,
        textTertiary = Neutral06LighterGrey,
        textHint = Neutral04Grey,
        border = Neutral03DarkGrey,
        pureBlack = Black,
        pureWhite = PureWhite
    )

    val customTypography = MovieAppTypography(
        bodyMedium = TextStyle(
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Medium,
            fontSize = TextSizing.size14,
            lineHeight = TextSizing.size18,
            letterSpacing = TextSizing.size0

        ),
        titleMedium = TextStyle(
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = TextSizing.size16,
            lineHeight = TextSizing.size20,
            letterSpacing = TextSizing.size0
        ),
        titleLarge = TextStyle(
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = TextSizing.size20,
            lineHeight = TextSizing.size26,
            letterSpacing = TextSizing.size0
        )
    )

    CompositionLocalProvider(
        LocalMovieAppColors provides customColors,
        LocalMovieAppTypography provides customTypography
    ) {
        MaterialTheme(content = content)
    }
}