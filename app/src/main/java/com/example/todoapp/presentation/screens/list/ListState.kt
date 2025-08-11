package com.example.todoapp.presentation.screens.list

import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.util.Action

data class ListState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val action: Action = Action.NO_ACTION,
    val tasks: List<ToDoTaskEntity> = emptyList(),
    val searchBarQuery: String = "",
    val titleTask: String = "",
)
