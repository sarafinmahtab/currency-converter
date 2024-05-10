package com.sarafinmahtab.currencyconverter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author Shamsul Arafin Mahtab
 * @since 28/07/21
 */

private val DarkColorPalette = darkColorScheme()

private val LightColorPalette = lightColorScheme(
    primary = ColorPrimaryLight,
    primaryContainer = ColorPrimaryContainerLight,
    onPrimary = ColorOnPrimaryLight,
    secondary = ColorSecondaryLight,
    secondaryContainer = ColorSecondaryContainerLight,
    onSecondary = ColorOnSecondaryLight,
    surface = ColorSurfaceLight,
    onSurface = ColorOnSurfaceLight,
    background = ColorBackgroundLight,
    onBackground = ColorOnBackgroundLight,
    outline = Color.LightGray,
)

@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
