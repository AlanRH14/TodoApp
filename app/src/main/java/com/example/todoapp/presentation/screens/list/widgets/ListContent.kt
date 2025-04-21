package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.presentation.screens.list.components.TaskItem
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    allTasks: List<ToDoTask>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: Priority,
    searchedTasks: List<ToDoTask>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    when {
        searchAppBarState == SearchAppBarState.TRIGGERED -> {
            HandleListContent(
                modifier = modifier,
                tasks = searchedTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

        sortState == Priority.NONE -> {
            HandleListContent(
                modifier = modifier,
                tasks = allTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

        sortState == Priority.LOW -> {
            HandleListContent(
                modifier = modifier,
                tasks = lowPriorityTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

        sortState == Priority.HIGH -> {
            HandleListContent(
                modifier = modifier,
                tasks = highPriorityTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
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
                /*TaskItem(
                    toDoTask = task,
                    navigationToTaskScreen = navigateToTaskScreen
                )*/

                val dismissState = rememberSwipeToDismissBoxState()

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromEndToStart = true,
                ) { }
            }
        }
    }
}