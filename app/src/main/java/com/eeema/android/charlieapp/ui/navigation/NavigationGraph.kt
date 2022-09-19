@file:OptIn(ExperimentalAnimationApi::class)

package com.eeema.android.charlieapp.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eeema.android.charlieapp.ui.characters.CharactersScreen
import com.eeema.android.charlieapp.ui.detail.DetailScreen
import com.eeema.android.charlieapp.ui.extensions.animatedComposable
import com.eeema.android.charlieapp.ui.extensions.navigate
import com.eeema.android.charlieapp.ui.model.Route
import com.eeema.android.charlieapp.ui.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun NavigationGraph() {
    val navigationController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navigationController,
        startDestination = Route.Splash.route
    ) {
        animatedComposable(Route.Splash.route) {
            SplashScreen(navigate = navigationController::navigate)
        }
        animatedComposable(Route.Characters.route) {
            CharactersScreen(navigate = navigationController::navigate)
        }
        animatedComposable(
            Route.Details.route.plus("/{characterId}"),
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) {
            DetailScreen()
        }
    }
}
