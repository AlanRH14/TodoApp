package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.presentation.screens.list.components.TaskItem
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    allTasks: List<ToDoTask>,
    searchedTasks: List<ToDoTask>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        HandleListContent(
            modifier = modifier,
            tasks = searchedTasks,
            navigateToTaskScreen = navigateToTaskScreen
        )
    } else {
        HandleListContent(
            modifier = modifier,
            tasks = allTasks,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@Composable
private fun HandleListContent(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent(
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(
                items = tasks,
                key = { task ->
                    task.id
                }
            ) { task ->
                TaskItem(
                    toDoTask = task,
                    navigationToTaskScreen = navigateToTaskScreen
                )
            }
        }
    }
}