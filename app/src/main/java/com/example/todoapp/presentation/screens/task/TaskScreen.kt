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
import androidx.navigation.NavHostController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.presentation.screens.list.mvi.ListEffect
import com.example.todoapp.presentation.screens.list.mvi.ListUIEvent
import com.example.todoapp.presentation.screens.task.widgets.TaskAppBar
import com.example.todoapp.presentation.screens.task.widgets.TaskContent
import com.example.todoapp.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel(),
    taskId: Int? = null,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val mContext = LocalContext.current

    BackHandler(
        onBack = { navController.popBackStack() }
    )

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ListEffect.ShowMessage -> {
                    Toast.makeText(mContext, effect.message, Toast.LENGTH_SHORT).show()
                }

                is ListEffect.NavigateToListScreen -> {
                    navController.navigate(Screen.List(action = effect.action)) {
                        popUpTo(Screen.List()) {
                            inclusive = true
                        }
                    }
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = taskId) {
        if (taskId != null) {
            viewModel.onEvent(TaskUIEvent.OnGetTaskSelected(taskID = taskId))
        }
    }

    LaunchedEffect(key1 = state.taskSelected) {
        if (state.taskSelected != null || taskId == -1) {
            viewModel.onEvent(TaskUIEvent.OnTaskFieldsUpdate(taskSelected = state.taskSelected))
        }
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                task = state.taskSelected,
                onEvent = viewModel::onEvent
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