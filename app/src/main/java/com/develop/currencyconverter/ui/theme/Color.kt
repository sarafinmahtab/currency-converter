package com.develop.currencyconverter.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author Shamsul Arafin Mahtab
 * @since 28/07/21
 */

val ColorPrimaryLight = Color(0xFF0277BD)
val ColorPrimaryVariantLight = Color(0xFF01579B)
val ColorOnPrimaryLight = Color.White
val ColorSecondaryLight = Color(0xFF00796B)
val ColorSecondaryVariantLight = Color(0xFF0097A7)
val ColorOnSecondaryLight = Color.White
val ColorSurfaceLight = Color.White
val ColorOnSurfaceLight = Color.Black
val ColorBackgroundLight = Color(0xFFF5F5F5)
val ColorOnBackgroundLight = Color.DarkGray


val ColorPrimaryDark = Color(0xFF3D3D3D)
val ColorPrimaryVariantDark = Color.DarkGray
val ColorOnPrimaryDark = Color.White
val ColorSecondaryDark = Color(0xFF00796B)
val ColorSecondaryVariantDark = Color(0xFF0097A7)
val ColorOnSecondaryDark = Color.White
val ColorSurfaceDark = Color(0xFF545454)
val ColorOnSurfaceDark = Color.White
val ColorBackgroundDark = Color(0xFF3D3D3D)
val ColorOnBackgroundDark = Color.White

val Colors.stroke: Color get() = Color.LightGray
val Colors.light: Color get() = if (isLight) Color.LightGray else Color.DarkGray


@Composable
fun appTextFieldColors(
    textColor: Color = MaterialTheme.colors.onSurface,
    disabledTextColor: Color = MaterialTheme.colors.light,
    backgroundColor: Color = Color.White,
    cursorColor: Color = MaterialTheme.colors.secondary,
    errorCursorColor: Color = MaterialTheme.colors.error,
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor
)
