package com.example.todoapp.presentation.screens.task

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.todoapp.presentation.screens.task.widgets.TaskAppBar

@Composable
fun TaskScreen() {
    Scaffold(
        topBar = {
            TaskAppBar()
        }
    ) { paddingValues ->
    }
}