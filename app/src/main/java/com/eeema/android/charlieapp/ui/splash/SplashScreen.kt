package com.eeema.android.charlieapp.ui.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eeema.android.charlieapp.R
import com.eeema.android.charlieapp.ui.components.ScreenScaffold
import com.eeema.android.charlieapp.ui.model.Route
import com.eeema.android.charlieapp.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    animationTime: Int = 500,
    navigate: (Route) -> Unit = {}
) {
    LaunchedEffect(key1 = true) {
        delay(animationTime.toLong())
        navigate(Route.Characters)
    }
    SplashContent()
}

@Composable
fun SplashContent() {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_brand),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(248.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AppTheme {
        ScreenScaffold { SplashScreen() }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashDarkScreenPreview() {
    AppTheme {
        ScreenScaffold { SplashScreen() }
    }
}
