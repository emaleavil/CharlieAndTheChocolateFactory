package com.eeema.android.charlieapp.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eeema.android.charlieapp.R
import com.eeema.android.charlieapp.ui.components.ListComponent
import com.eeema.android.charlieapp.ui.components.ScreenScaffold
import com.eeema.android.charlieapp.ui.components.SearchComponent

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    navigate: (String) -> Unit = {}
) {
    ScreenScaffold {
        Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
            SearchComponent(
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(R.string.characters_search_placeholder),
                onSearch = viewModel::filter
            )
            ListComponent(
                modifier = Modifier.padding(top = 32.dp),
                viewModel.state,
                viewModel::retry,
                navigate
            ) { character, navigate -> CharacterItem(character = character, navigate = navigate) }
        }
    }
}
