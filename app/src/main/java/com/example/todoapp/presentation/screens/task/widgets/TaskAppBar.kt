package com.example.todoapp.presentation.screens.task.widgets

import androidx.compose.runtime.Composable
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.presentation.screens.task.components.ExistingTaskAppBar
import com.example.todoapp.presentation.screens.task.components.NewTaskAppBar
import com.example.todoapp.util.Action

@Composable
fun TaskAppBar(
    task: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (task == null) {
        NewTaskAppBar(
            navigateToListScreen = { navigateToListScreen(it) }
        )
    } else {
        ExistingTaskAppBar(
            task = task,
            navigateToListScreen = { navigateToListScreen(it) }
        )
    }
}