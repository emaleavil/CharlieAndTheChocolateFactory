package com.eeema.android.charlieapp.ui.model

sealed class Route(
    val route: String,
    val removePreviousScreens: Boolean = false
) {
    object Splash : Route("splash")
    object Characters : Route("characters", true)
    object Details : Route("detail", false)
}
