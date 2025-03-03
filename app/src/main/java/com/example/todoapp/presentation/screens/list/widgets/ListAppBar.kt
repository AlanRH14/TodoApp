package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.screens.list.components.DefaultListAppBar
import com.example.todoapp.presentation.screens.list.components.ListSearchAppBar
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onSearch: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onActionClicked: (SearchAppBarState) -> Unit,
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    onActionClicked(SearchAppBarState.OPENED)
                },
                onSortClicked = {},
                onDeleteClicked = {},
            )
        }

        else -> {
            ListSearchAppBar(
                text = searchTextState,
                onTextChange = { onSearchTextChange(it) },
                onSearchActionClicked = onSearch,
                onCloseClicked = { onActionClicked(SearchAppBarState.CLOSED) }
            )
        }
    }
}