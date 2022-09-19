package com.eeema.android.charlieapp.ui.characters

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eeema.android.charlieapp.R
import com.eeema.android.charlieapp.ui.components.ScreenScaffold
import com.eeema.android.charlieapp.ui.extensions.clickableSingle
import com.eeema.android.charlieapp.ui.extensions.genderImage
import com.eeema.android.charlieapp.ui.model.Route
import com.eeema.android.charlieapp.ui.theme.AppTheme
import com.eeema.android.data.model.Character

@Composable
fun CharacterItem(
    character: Character,
    navigate: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickableSingle { navigate(Route.Details.route.plus("/${character.id}")) },
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CharacterImage(character.image)
            CharacterBody(character.fullName, character.profession, character.genderImage())
        }
    }
}

@Composable
fun CharacterBody(
    title: String,
    body: String,
    iconId: Int?
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
    ) {
        Column(
            Modifier.weight(0.70f).align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = body, style = MaterialTheme.typography.body2, maxLines = 1)
        }
        Spacer(Modifier.weight(0.05f))
        if (iconId != null) {
            Image(
                painter = painterResource(iconId),
                modifier = Modifier.weight(0.25f).padding(16.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = stringResource(R.string.characters_gender_description)
            )
        }
    }
}

@Composable
fun CharacterImage(url: String) {
    Box(
        Modifier.fillMaxWidth().wrapContentHeight()
            .background(color = MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_brand),
            contentDescription = stringResource(R.string.characters_image_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(16.dp).clip(CircleShape).size(148.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    AppTheme {
        ScreenScaffold {
            CharacterItem(
                character = Character(
                    "Manuel",
                    "Vivo",
                    "M",
                    "",
                    "Developer",
                    "manuel.vivo@gmail.com",
                    31,
                    0,
                    "Spain",
                    67
                )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharactersDarkScreenPreview() {
    AppTheme {
        ScreenScaffold {
            CharacterItem(
                character = Character(
                    "Manuel",
                    "Vivo",
                    "M",
                    "",
                    "Developer",
                    "manuel.vivo@gmail.com",
                    31,
                    0,
                    "Spain",
                    67
                )
            )
        }
    }
}
