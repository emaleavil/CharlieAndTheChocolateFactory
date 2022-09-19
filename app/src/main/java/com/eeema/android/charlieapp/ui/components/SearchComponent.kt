@file:OptIn(ExperimentalComposeUiApi::class)

package com.eeema.android.charlieapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.eeema.android.charlieapp.R
import com.eeema.android.charlieapp.ui.extensions.outlineTextFieldColor
import com.eeema.android.charlieapp.ui.theme.AppTheme

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    placeholder: String,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = MaterialTheme.outlineTextFieldColor,
    onSearch: (String) -> Unit
) {

    var searchInput by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            value = searchInput,
            onValueChange = { searchInput = it },
            singleLine = true,
            shape = shape,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onSearch(searchInput)
                        keyboardController?.hide()
                    },
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    modifier = Modifier.wrapContentSize()
                )
            },
            colors = colors,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchInput)
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchComponentPreview() {
    AppTheme {
        SearchComponent(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Search"
        ) {
        }
    }
}
