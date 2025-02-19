package com.example.todoapp.presentation.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.presentation.list.components.ListFab
import com.example.todoapp.presentation.list.widget.ListAppBar

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit
) {
    Scaffold(
        topBar = { ListAppBar() },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) { paddingValues ->

    }
}

@Composable
@Preview
fun ListScreenPreview() {
    ListScreen { }
}