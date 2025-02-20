package com.example.todoapp.presentation.list.widget

import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.list.components.DefaultListAppBar
import com.example.todoapp.presentation.list.components.ListSearchAppBar
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onSearchClicked: (SearchAppBarState) -> Unit,
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
                text = "",
                onTextChange = {},
                onSearchClicked = {},
                onCloseClicked = {}
            )
        }
    }
}