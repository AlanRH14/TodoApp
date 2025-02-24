package com.example.todoapp.presentation.screens.task.widgets

import androidx.compose.runtime.Composable
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.presentation.screens.task.components.ExistingTaskAppBar
import com.example.todoapp.presentation.screens.task.components.NewTaskAppBar

@Composable
fun TaskAppBar(
    task: ToDoTask?
) {
    if (task == null) {
        NewTaskAppBar(
            navigateToListScreen = {}
        )
    } else {
        ExistingTaskAppBar(
            task = task,
            navigateToListScreen = {}
        )
    }
}