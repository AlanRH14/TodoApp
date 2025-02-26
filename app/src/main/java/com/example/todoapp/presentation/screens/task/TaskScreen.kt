package com.example.todoapp.presentation.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.todoapp.presentation.screens.task.widgets.TaskAppBar
import com.example.todoapp.presentation.screens.task.widgets.TaskContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    taskId: Int? = null
) {
    val title by sharedViewModel.title.collectAsState()
    val description by sharedViewModel.description.collectAsState()
    val priority by sharedViewModel.priority.collectAsState()

    if (taskId != null) {
        sharedViewModel.getSelectedTask(taskId = taskId)
    }
    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    LaunchedEffect(key1 = taskId) {
        sharedViewModel.updateTaskFields(selectedTask)
    }

    Scaffold(
        topBar = {
            TaskAppBar(task = selectedTask)
        }
    ) { paddingValues ->
        TaskContent(
            modifier = Modifier.padding(paddingValues),
            title = title,
            onTitleChange = { sharedViewModel.setTitleTask(it) },
            description = description,
            onDescriptionChange = { sharedViewModel.setDescriptionTask(it) },
            priority = priority,
            onPrioritySelected = { sharedViewModel.setPriorityTask(it) }
        )
    }
}