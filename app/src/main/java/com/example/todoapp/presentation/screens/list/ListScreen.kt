package com.example.todoapp.presentation.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.todoapp.data.model.Priority
import com.example.todoapp.presentation.screens.list.components.DisplaySnackBar
import com.example.todoapp.presentation.screens.list.components.ListFab
import com.example.todoapp.presentation.screens.list.widgets.ListAppBar
import com.example.todoapp.presentation.screens.list.widgets.ListContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    mAction: Action,
    sharedViewModel: SharedViewModel = koinViewModel(),
    viewModel: ListViewModel = koinViewModel(),
    navigateToTaskScreen: (Int) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ListUIEvent.GetTasks(priority = Priority.NONE))

        viewModel.effect.collectLatest { effect ->
            when(effect) {
                is ListEffect.SortTasks -> {}

                else -> Unit
            }
        }
    }










    var rememberAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

    LaunchedEffect(key1 = rememberAction) {
        if (rememberAction != mAction) {
            rememberAction = mAction
            sharedViewModel.updateAction(mAction)
        }
    }

    val action by sharedViewModel.action.collectAsState()
    val allTask by sharedViewModel.tasks.collectAsState()
    val searchTasks by sharedViewModel.searchTasks.collectAsState()
    val searchAppBarState by sharedViewModel.searchAppBarState.collectAsState()
    val searchTextAppBarState by sharedViewModel.searchTextAppBarState.collectAsState()
    val title by sharedViewModel.title.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val scaffoldState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = sortState) {
        sharedViewModel.getTasks(sortState = sortState)
    }
    sharedViewModel.handleDatabaseActions(action = action)

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onUndoClicked = { newAction ->
            sharedViewModel.updateAction(newAction)
        },
        onCompleteAction = { newAction ->
            sharedViewModel.updateAction(newAction)
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
                    sharedViewModel.setSearchAppBarState(appBarState)
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
            tasks = if (searchAppBarState == SearchAppBarState.TRIGGERED) {
                searchTasks
            } else {
                allTask
            },
            onSwipeToDelete = { action, task ->
                sharedViewModel.updateAction(action = action)
                sharedViewModel.updateTaskFields(selectedTask = task)
                scaffoldState.currentSnackbarData?.dismiss()
            },
            navigateToTaskScreen = navigateToTaskScreen,
        )
    }
}
