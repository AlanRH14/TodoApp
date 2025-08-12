package com.example.todoapp.presentation.screens.list

import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.Action

data class ListState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val action: Action = Action.NO_ACTION,
    val tasks: List<ToDoTaskEntity> = emptyList(),
    val searchBarQuery: String = "",
    val taskSelected: ToDoTaskEntity? = null,
    val idTask: Int = 0,
    val titleTask: String = "",
    val description: String = "",
    val priority: Priority = Priority.NONE
)
