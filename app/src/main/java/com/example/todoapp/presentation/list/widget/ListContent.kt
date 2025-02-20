package com.example.todoapp.presentation.list.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.presentation.list.components.TaskItem

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    toDoTasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn {
            items(
                items = toDoTasks,
                key = { task ->
                    task.id
                }
            ) { task ->
                TaskItem(
                    toDoTask = task,
                    navigationToTaskScreen = { navigateToTaskScreen(task.id) }
                )
            }
        }
    }
}