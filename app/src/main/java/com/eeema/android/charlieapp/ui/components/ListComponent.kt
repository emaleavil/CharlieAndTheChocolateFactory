package com.eeema.android.charlieapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.eeema.android.charlieapp.ui.extensions.isEmpty
import com.eeema.android.charlieapp.ui.extensions.isError
import com.eeema.android.charlieapp.ui.extensions.isLoading
import com.eeema.android.charlieapp.ui.extensions.isRequestingMoreItems
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T : Any> ListComponent(
    modifier: Modifier = Modifier,
    state: StateFlow<PagingData<T>>,
    retry: () -> Unit = {},
    navigate: (String) -> Unit = {},
    itemContent: @Composable (item: T, navigation: (String) -> Unit) -> Unit
) {

    val listState = rememberLazyListState()
    val data = state.collectAsLazyPagingItems()

    when {
        data.isLoading() -> LoaderScreen()
        data.isError() -> ErrorScreen(retry = retry)
        data.isEmpty() -> EmptyScreen(retry = retry)
        else -> {
            LazyColumn(modifier = modifier, state = listState) {
                items(items = data) { item -> item?.let { itemContent(it, navigate) } }
                if (data.isRequestingMoreItems()) {
                    LoadingItem()
                }
            }
        }
    }
}

private fun LazyListScope.LoadingItem() {
    item {
        CircularProgressIndicator(
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}
