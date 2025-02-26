package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.screens.list.components.DefaultListAppBar
import com.example.todoapp.presentation.screens.list.components.ListSearchAppBar
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onSearchClicked: (SearchAppBarState) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onCloseClicked: (SearchAppBarState) -> Unit,
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    onSearchClicked(SearchAppBarState.OPENED)
                },
                onSortClicked = {},
                onDeleteClicked = {},
            )
        }

        else -> {
            ListSearchAppBar(
                text = searchTextState,
                onTextChange = { onSearchTextChange(it) },
                onSearchClicked = {},
                onCloseClicked = { onCloseClicked(SearchAppBarState.CLOSED) }
            )
        }
    }
}