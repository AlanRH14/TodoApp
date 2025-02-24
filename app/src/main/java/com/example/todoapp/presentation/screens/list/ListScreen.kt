package com.example.todoapp.presentation.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.todoapp.presentation.screens.list.components.ListFab
import com.example.todoapp.presentation.screens.list.widgets.ListAppBar
import com.example.todoapp.presentation.screens.list.widgets.ListContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }
    val allTask by sharedViewModel.allTask.collectAsState()
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
        ListContent(
            modifier = Modifier.padding(paddingValues),
            toDoTasks = allTask,
        ) { idTask ->
            navigateToTaskScreen(idTask)
        }
    }
}
