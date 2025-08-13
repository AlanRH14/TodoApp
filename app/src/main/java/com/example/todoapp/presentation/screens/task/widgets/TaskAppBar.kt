package com.example.todoapp.presentation.screens.task.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.presentation.components.DisplayAlertDialog
import com.example.todoapp.presentation.screens.task.components.ExistingTaskAppBar
import com.example.todoapp.presentation.screens.task.components.NewTaskAppBar
import com.example.todoapp.util.Action

@Composable
fun TaskAppBar(
    task: ToDoTaskEntity?,
    navigateToListScreen: (Action) -> Unit,
) {
    if (task == null) {
        NewTaskAppBar(
            navigateToListScreen = navigateToListScreen
        )
    } else {
        var openDialog by remember { mutableStateOf(false) }

        DisplayAlertDialog(
            title = stringResource(R.string.delete_task, task.title ?: ""),
            message = stringResource(R.string.delete_task_confirmation, task.title ?: ""),
            openDialog = openDialog,
            closeDialog = { openDialog = false },
            onConfirmClicked = { navigateToListScreen(Action.DELETE) }
        )

        ExistingTaskAppBar(
            task = task,
            navigateToListScreen = navigateToListScreen,
            onDeleteClicked = {
                openDialog = true
            }
        )
    }
}