package com.example.todoapp.presentation.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.todoapp.presentation.screens.list.components.DisplaySnackBar
import com.example.todoapp.presentation.screens.list.components.ListFab
import com.example.todoapp.presentation.screens.list.widgets.ListAppBar
import com.example.todoapp.presentation.screens.list.widgets.ListContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListScreen(
    mAction: Action,
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
        sharedViewModel.readSortState()
    }
    LaunchedEffect(key1 = mAction) {
        sharedViewModel.updateAction(mAction)
    }
    val action by sharedViewModel.action.collectAsState()
    val allTask by sharedViewModel.allTask.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val searchAppBarState by sharedViewModel.searchAppBarState.collectAsState()
    val searchTextAppBarState by sharedViewModel.searchTextAppBarState.collectAsState()
    val title by sharedViewModel.title.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriority by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriority by sharedViewModel.highPriorityTasks.collectAsState()
    val scaffoldState = remember { SnackbarHostState() }

    sharedViewModel.handleDatabaseActions(action = action)

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onUndoClicked = {
            sharedViewModel.updateAction(it)
        },
        taskTitle = title,
        action = action,
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        },
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextAppBarState,
                onSearch = { query ->
                    sharedViewModel.setSearchAppBarState(SearchAppBarState.TRIGGERED)
                    sharedViewModel.searchTasks(query)
                },
                onSearchTextChange = { text -> sharedViewModel.setSearchTextAppBarState(text) },
                onSearchActionClicked = { appBarState ->
                    sharedViewModel.setSearchAppBarState(
                        appBarState
                    )
                },
                onSortClicked = { sharedViewModel.persistSortState(it) },
                onBarActionClicked = { action -> sharedViewModel.updateAction(action) }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) { paddingValues ->
        ListContent(
            modifier = Modifier.padding(paddingValues),
            allTasks = allTask,
            lowPriorityTasks = lowPriority,
            highPriorityTasks = highPriority,
            sortState = sortState,
            searchedTasks = searchedTasks,
            searchAppBarState = searchAppBarState,
            onSwipeToDelete = { action, task ->
                sharedViewModel.updateAction(action = action)
                sharedViewModel.updateTaskFields(selectedTask = task)
            },
            navigateToTaskScreen = navigateToTaskScreen,
        )
    }
}
