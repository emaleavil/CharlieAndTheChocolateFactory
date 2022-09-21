package com.eeema.android.charlieapp.ui.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eeema.android.charlieapp.R
import com.eeema.android.charlieapp.ui.components.ErrorScreen
import com.eeema.android.charlieapp.ui.components.LoaderScreen
import com.eeema.android.charlieapp.ui.components.ScreenScaffold
import com.eeema.android.charlieapp.ui.theme.AppTheme
import com.eeema.android.data.model.Character

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state: DetailState by viewModel.state.collectAsState()
    when (state) {
        is DetailState.Loading -> LoaderScreen()
        is DetailState.Data -> DetailContent((state as DetailState.Data).data)
        is DetailState.Failed -> ErrorScreen()
    }
}

@Composable
fun DetailContent(character: Character) {
    ScreenScaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 32.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_brand),
                contentDescription = stringResource(R.string.characters_image_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(16.dp)
                    .height(300.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = character.fullName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colors.primaryVariant
            )
            Spacer(Modifier.height(32.dp))
            DetailRow(
                stringResource(R.string.detail_email_title),
                character.email
            )
            Spacer(Modifier.height(8.dp))
            DetailRow(
                stringResource(R.string.detail_profession_title),
                character.profession
            )
            Spacer(Modifier.height(8.dp))
            DetailRow(
                stringResource(R.string.detail_country_title),
                character.country
            )
        }
    }
}

@Composable
fun DetailRow(title: String, value: String) {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            color = MaterialTheme.colors.primaryVariant
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppTheme {
        ScreenScaffold {
            // DetailContent(Character()
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailDarkScreenPreview() {
    AppTheme {
        ScreenScaffold {
            // DetailContent(Character())
        }
    }
}
