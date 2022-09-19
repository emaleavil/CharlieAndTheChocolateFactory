package com.eeema.android.charlieapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = mainColorDark,
    primaryVariant = mainVariantColorDark,
    secondary = secondaryColorDark,
    secondaryVariant = secondaryVariantColorDark,
    surface = backgroundColorDark,
    background = backgroundColorDark,
    onPrimary = onPrimaryColorDark,
    onSecondary = onPrimaryColorDark,
    onBackground = onPrimaryColorDark
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = mainColorLight,
    primaryVariant = mainVariantColorLight,
    secondary = secondaryColorLight,
    secondaryVariant = secondaryVariantColorLight,
    surface = backgroundColorLight,
    background = backgroundColorLight,
    onPrimary = onPrimaryColorLight,
    onBackground = onPrimaryColorLight,
    onSecondary = onPrimaryColorLight
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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
