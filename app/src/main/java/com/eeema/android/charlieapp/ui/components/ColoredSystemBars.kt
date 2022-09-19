package com.eeema.android.charlieapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * This class apply colors to android status bars (notification and navigation bars)
 */
@Composable
fun ColoredSystemBars(
    color: Color = MaterialTheme.colors.background,
    useDarkIcons: Boolean = false
) {
    val systemUiController = rememberSystemUiController()
    SideEffect { systemUiController.setSystemBarsColor(color = color, darkIcons = useDarkIcons) }
}
