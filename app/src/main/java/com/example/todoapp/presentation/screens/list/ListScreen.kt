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
import androidx.navigation.NavHostController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.presentation.mvi.ListEffect
import com.example.todoapp.presentation.mvi.ListUIEvent
import com.example.todoapp.presentation.screens.list.components.DisplaySnackBar
import com.example.todoapp.presentation.screens.list.components.ListFab
import com.example.todoapp.presentation.screens.list.widgets.ListAppBar
import com.example.todoapp.presentation.screens.list.widgets.ListContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Action
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    action: Action = Action.NO_ACTION,
    viewModel: SharedViewModel = koinViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ListUIEvent.GetTasks(priority = state.priority))
        viewModel.onEvent(ListUIEvent.OnReadSortState)
        if (action != state.action) {
            viewModel.onEvent(ListUIEvent.OnActionUpdate(action = action))
        }
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ListEffect.NavigateToTaskScreen -> {
                    navController.navigate(Screen.Task(taskId = effect.taskID))
                }

                else -> Unit
            }
        }
    }

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
                searchText = state.searchText,
                onEvent = viewModel::onEvent,
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = {
                viewModel.onEvent(ListUIEvent.OnNavigateToTaskScreen(taskID = it))
            })
        }
    ) { paddingValues ->
        ListContent(
            modifier = Modifier.padding(paddingValues),
            tasks = state.tasks,
            onEvent = viewModel::onEvent,
        )
    }
}
