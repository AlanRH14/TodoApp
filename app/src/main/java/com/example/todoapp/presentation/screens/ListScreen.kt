package com.example.todoapp.presentation.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.todoapp.presentation.list.components.ListFab
import com.example.todoapp.presentation.list.widget.ListAppBar
import com.example.todoapp.presentation.viewmodel.SharedViewModel

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchAppBarState by sharedViewModel.searchAppBarState.collectAsState()
    val searchTextAppBarState by sharedViewModel.searchTextAppBarState.collectAsState()

    Scaffold(
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextAppBarState,
                onSearchClicked = { appBarState -> sharedViewModel.setSearchAppBarState(appBarState) },
                onSearchTextChange = { text -> sharedViewModel.setSearchTextAppBarState(text) },
                onCloseClicked = { appBarState -> sharedViewModel.setSearchAppBarState(appBarState) }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) { paddingValues ->

    }
}
