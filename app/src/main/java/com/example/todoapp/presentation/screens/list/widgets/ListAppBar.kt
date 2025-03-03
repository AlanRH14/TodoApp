package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.screens.list.components.DefaultListAppBar
import com.example.todoapp.presentation.screens.list.components.ListSearchAppBar
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onSearch: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchActionClicked: (SearchAppBarState) -> Unit,
    onBarActionClicked: (Action) -> Unit
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    onSearchActionClicked(SearchAppBarState.OPENED)
                },
                onSortClicked = {},
                onDeleteClicked = { onBarActionClicked(Action.DELETE_ALL) },
            )
        }

        else -> {
            ListSearchAppBar(
                text = searchTextState,
                onTextChange = { onSearchTextChange(it) },
                onSearchActionClicked = onSearch,
                onCloseClicked = { onSearchActionClicked(SearchAppBarState.CLOSED) }
            )
        }
    }
}