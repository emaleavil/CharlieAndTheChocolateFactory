package com.eeema.android.charlieapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eeema.android.charlieapp.R
import com.eeema.android.charlieapp.ui.theme.AppTheme

@Composable
fun EmptyScreen(
    retry: (() -> Unit)? = null
) {
    ScreenScaffold {
        Column(
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .wrapContentSize()
                    .background(color = MaterialTheme.colors.onPrimary, shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_empty),
                    modifier = Modifier.size(200.dp).padding(32.dp),
                    contentDescription = stringResource(R.string.empty_image_description)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                stringResource(R.string.empty_title),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.height(32.dp))
            if (retry != null) {
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = retry,
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(stringResource(R.string.retry_button_title))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    AppTheme {
        ScreenScaffold {
            EmptyScreen()
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun EmptyScreenDarkPreview() {
    AppTheme {
        ScreenScaffold {
            EmptyScreen()
        }
    }
}
