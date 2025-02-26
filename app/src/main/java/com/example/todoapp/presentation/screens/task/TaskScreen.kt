package com.example.todoapp.presentation.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.todoapp.data.model.Priority
import com.example.todoapp.presentation.screens.task.widgets.TaskAppBar
import com.example.todoapp.presentation.screens.task.widgets.TaskContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    taskId: Int? = null
) {
    if (taskId != null) {
        sharedViewModel.getSelectedTask(taskId = taskId)
    }
    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    Scaffold(
        topBar = {
            TaskAppBar(task = selectedTask)
        }
    ) { paddingValues ->
        TaskContent(
            modifier = Modifier.padding(paddingValues),
            title = "",
            onTitleChange = {},
            description = "",
            onDescriptionChange = {},
            priority = Priority.LOW,
            onPrioritySelected = {}
        )
    }
}