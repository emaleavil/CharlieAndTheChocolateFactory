package com.eeema.android.charlieapp.ui.extensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.isLoading(): Boolean {
    return refresh is LoadState.Loading
}

fun CombinedLoadStates.isRequestingMoreItems(): Boolean {
    return append is LoadState.Loading
}

fun CombinedLoadStates.isError(): Boolean {
    return append is LoadState.Error || refresh is LoadState.Error
}
