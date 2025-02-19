package com.example.todoapp.presentation.list.widget

import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.list.components.DefaultListAppBar

@Composable
fun ListAppBar() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {},
    )
}