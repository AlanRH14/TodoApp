package com.example.todoapp.presentation.screens.task.mvi

import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.util.Action

data class TaskState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val taskSelected: ToDoTask? = null,
    val action: Action = Action.NO_ACTION,
    val taskID: Int = 0,
    val titleTask: String = "",
    val description: String = "",
    val priority: Priority = Priority.NONE,
)
