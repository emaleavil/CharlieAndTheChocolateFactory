package com.eeema.android.data.server

sealed interface ResponseStatus {
    object Ok : ResponseStatus
    object NotFound : ResponseStatus
    object ServerNotFound : ResponseStatus
    data class CustomOk(val fileName: String) : ResponseStatus
}
