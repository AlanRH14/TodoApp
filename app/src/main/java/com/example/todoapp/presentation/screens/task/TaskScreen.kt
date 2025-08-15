package com.example.todoapp.presentation.screens.task

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.todoapp.presentation.mvi.ListEffect
import com.example.todoapp.presentation.screens.list.ListUIEvent
import com.example.todoapp.presentation.screens.task.widgets.TaskAppBar
import com.example.todoapp.presentation.screens.task.widgets.TaskContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Action
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreen(
    viewModel: SharedViewModel = koinViewModel(),
    taskId: Int? = null,
    navigateToListScreen: (Action) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val mContext = LocalContext.current

    BackHandler(
        onBack = { navigateToListScreen(Action.NO_ACTION) }
    )

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ListEffect.ShowMessage -> {
                    Toast.makeText(mContext, effect.message, Toast.LENGTH_SHORT).show()
                }

                is ListEffect.NavigateToListScreen -> {
                    navigateToListScreen(effect.action)
                }
            }
        }
    }

    LaunchedEffect(key1 = taskId) {
        if (taskId != null) {
            viewModel.onEvent(ListUIEvent.OnGetTaskSelected(taskID = taskId))
        }
    }

    LaunchedEffect(key1 = state.taskSelected) {
        if (state.taskSelected != null || taskId == -1) {
            viewModel.onEvent(ListUIEvent.OnTaskFieldsUpdate(taskSelected = state.taskSelected))
        }
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                task = state.taskSelected,
                navigateToListScreen = { action ->
                    viewModel.onEvent(ListUIEvent.OnNavigateToListScreen(action = action))
                }
            )
        }
    ) { paddingValues ->
        TaskContent(
            modifier = Modifier.padding(paddingValues),
            title = state.titleTask,
            onEvent = viewModel::onEvent,
            description = state.description,
            priority = state.priority,
        )
    }
}