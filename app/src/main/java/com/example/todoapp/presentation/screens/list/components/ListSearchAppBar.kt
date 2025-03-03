package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.todoapp.R
import com.example.todoapp.ui.theme.TopAppBarBackgroundColor
import com.example.todoapp.ui.theme.TopAppBarContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchActionClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopAppBarBackgroundColor
        ),
        title = {
            TextField(
                value = text,
                onValueChange = { onTextChange(it) },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.74F),
                        text = stringResource(R.string.search_placeholder),
                        color = TopAppBarContentColor
                    )
                },
                textStyle = TextStyle(
                    color = TopAppBarContentColor,
                    fontSize = Typography().bodyLarge.fontSize
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchActionClicked(text)
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TopAppBarBackgroundColor,
                    unfocusedContainerColor = TopAppBarBackgroundColor,
                )
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.alpha(0.74F),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = TopAppBarContentColor
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = TopAppBarContentColor
                )
            }
        }
    )
}