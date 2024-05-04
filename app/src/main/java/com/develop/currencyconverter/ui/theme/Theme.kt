package com.develop.currencyconverter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * @author Shamsul Arafin Mahtab
 * @since 28/07/21
 */

private val DarkColorPalette = darkColorScheme(
    primary = ColorPrimaryDark,
    primaryContainer = ColorPrimaryVariantDark,
    onPrimary = ColorOnPrimaryDark,
    secondary = ColorSecondaryDark,
    secondaryContainer = ColorSecondaryVariantDark,
    onSecondary = ColorOnSecondaryDark,
    surface = ColorSurfaceDark,
    onSurface = ColorOnSurfaceDark,
    background = ColorBackgroundDark,
    onBackground = ColorOnBackgroundDark
)

private val LightColorPalette = lightColorScheme(
    primary = ColorPrimaryLight,
    primaryContainer = ColorPrimaryVariantLight,
    onPrimary = ColorOnPrimaryLight,
    secondary = ColorSecondaryLight,
    secondaryContainer = ColorSecondaryVariantLight,
    onSecondary = ColorOnSecondaryLight,
    surface = ColorSurfaceLight,
    onSurface = ColorOnSurfaceLight,
    background = ColorBackgroundLight,
    onBackground = ColorOnBackgroundLight
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
