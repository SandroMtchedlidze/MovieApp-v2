package com.example.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Color.Neutral01Black
import com.example.ui.theme.Color.Neutral02DarkestGrey
import com.example.ui.theme.Color.Neutral03DarkGrey
import com.example.ui.theme.Color.Neutral04Grey
import com.example.ui.theme.Color.Neutral05LightGrey
import com.example.ui.theme.Color.Neutral06LighterGrey
import com.example.ui.theme.Color.Neutral08Whisper
import com.example.ui.theme.Color.YellowPrimary


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
    val border: Color
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
        Color.Unspecified
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
        border = Neutral03DarkGrey
    )

    val customTypography = MovieAppTypography(
        bodyMedium = TextStyle(
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.sp
        ),
        titleLarge = TextStyle(
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.sp
        )
    )

    CompositionLocalProvider(
        LocalMovieAppColors provides customColors,
        LocalMovieAppTypography provides customTypography
    ) {
        MaterialTheme(content = content)
    }
}