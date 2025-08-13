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
    viewModel: SharedViewModel = koinViewModel(),
    navigateToTaskScreen: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ListUIEvent.GetTasks(priority = Priority.NONE))
        viewModel.onEvent(ListUIEvent.OnReadSortState)
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                else -> Unit
            }
        }
    }


    var rememberAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

    LaunchedEffect(key1 = rememberAction) {
        if (rememberAction != mAction) {
            rememberAction = mAction
            viewModel.onEvent(ListUIEvent.OnActionUpdate(action = mAction))
        }
    }
    val scaffoldState = remember { SnackbarHostState() }

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onEvent = viewModel::onEvent,
        taskTitle = state.titleTask,
        action = state.action,
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        },
        topBar = {
            ListAppBar(
                searchAppBarState = state.searchAppBarState,
                searchText = state.searchBarState,
                onEvent = viewModel::onEvent,
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) { paddingValues ->
        ListContent(
            modifier = Modifier.padding(paddingValues),
            tasks = if (state.searchAppBarState == SearchAppBarState.TRIGGERED) {
                state.searchTasks
            } else {
                state.tasks
            },
            onSwipeToDelete = { action, task ->
                viewModel.onEvent(ListUIEvent.OnActionUpdate(action = action))
                //sharedViewModel.updateTaskFields(selectedTask = task)
                scaffoldState.currentSnackbarData?.dismiss()
            },
            navigateToTaskScreen = navigateToTaskScreen,
        )
    }
}
