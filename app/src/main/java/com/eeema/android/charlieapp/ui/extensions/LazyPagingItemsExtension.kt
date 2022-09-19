package com.eeema.android.charlieapp.ui.extensions

import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean =
    this.itemCount == 0 && !this.isLoading() && !this.isError()

fun <T : Any> LazyPagingItems<T>.isLoading(): Boolean =
    this.loadState.isLoading()

fun <T : Any> LazyPagingItems<T>.isRequestingMoreItems(): Boolean =
    this.loadState.isRequestingMoreItems()

fun <T : Any> LazyPagingItems<T>.isError(): Boolean =
    this.loadState.isError()
