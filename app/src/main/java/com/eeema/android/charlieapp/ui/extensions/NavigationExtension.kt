@file:OptIn(ExperimentalAnimationApi::class)

package com.eeema.android.charlieapp.ui.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.eeema.android.charlieapp.ui.model.Route
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) =
        {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(1000)
            )
        },
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) =
        {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(1000)
            )
        },
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) =
        {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(1000)
            )
        },
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) =
        {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(1000)
            )
        },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        arguments,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popExitTransition = popExitTransition,
        popEnterTransition = popEnterTransition,
        content = content
    )
}

fun NavHostController.navigate(nextRoute: Route) {
    navigate(nextRoute.route) {
        popUpTo(currentDestination?.route, nextRoute.removePreviousScreens)
    }
}

private fun NavOptionsBuilder.popUpTo(route: String?, removeInclusive: Boolean = false) {
    if (!route.isNullOrBlank()) {
        popUpTo(route) {
            inclusive = removeInclusive
        }
    }
}
