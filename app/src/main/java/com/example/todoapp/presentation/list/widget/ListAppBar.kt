package com.example.todoapp.presentation.list.widget

import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.list.components.DefaultListAppBar
import com.example.todoapp.presentation.list.components.ListSearchAppBar

@Composable
fun ListAppBar() {
    /*DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {},
    )*/

    ListSearchAppBar(
        text = "",
        onTextChange = {},
        onSearchClicked = {},
        onCloseClicked = {}
    )
}