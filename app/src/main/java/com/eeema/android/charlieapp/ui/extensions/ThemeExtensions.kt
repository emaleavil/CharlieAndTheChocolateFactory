package com.eeema.android.charlieapp.ui.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable

val MaterialTheme.outlineTextFieldColor: TextFieldColors
    @Composable
    get() = TextFieldDefaults.outlinedTextFieldColors(
        textColor = colors.onPrimary,
        placeholderColor = colors.onPrimary,
        cursorColor = colors.onPrimary,
        focusedBorderColor = colors.onPrimary,
        unfocusedBorderColor = colors.onPrimary,
        trailingIconColor = colors.onPrimary,
        leadingIconColor = colors.onPrimary
    )
