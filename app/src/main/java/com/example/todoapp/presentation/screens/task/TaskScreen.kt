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
import com.example.todoapp.presentation.screens.task.widgets.TaskAppBar
import com.example.todoapp.presentation.screens.task.widgets.TaskContent
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Action

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    taskId: Int? = null,
    navigateToListScreen: (Action) -> Unit
) {
    val title by sharedViewModel.title.collectAsState()
    val description by sharedViewModel.description.collectAsState()
    val priority by sharedViewModel.priority.collectAsState()
    val selectedTask by sharedViewModel.selectedTask.collectAsState()
    val mContext = LocalContext.current

    BackHandler(
        onBack = { navigateToListScreen(Action.NO_ACTION) }
    )

    LaunchedEffect(key1 = taskId) {
        if (taskId != null) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }
    }

    LaunchedEffect(key1 = selectedTask) {
        if (selectedTask != null || taskId == -1) {
            sharedViewModel.updateTaskFields(selectedTask)
        }
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                task = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            Toast.makeText(mContext, "Fields Empty.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
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