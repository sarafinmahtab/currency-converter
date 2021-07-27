package com.develop.currencyconverter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

/**
 * @author Shamsul Arafin Mahtab
 * @since 28/07/21
 */

private val DarkColorPalette = darkColors(
    primary = ColorPrimaryDark,
    primaryVariant = ColorPrimaryVariantDark,
    onPrimary = ColorOnPrimaryDark,
    secondary = ColorSecondaryDark,
    secondaryVariant = ColorSecondaryVariantDark,
    onSecondary = ColorOnSecondaryDark,
    surface = ColorSurfaceDark,
    onSurface = ColorOnSurfaceDark,
    background = ColorBackgroundDark,
    onBackground = ColorOnBackgroundDark
)

private val LightColorPalette = lightColors(
    primary = ColorPrimaryLight,
    primaryVariant = ColorPrimaryVariantLight,
    onPrimary = ColorOnPrimaryLight,
    secondary = ColorSecondaryLight,
    secondaryVariant = ColorSecondaryVariantLight,
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
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
