package com.example.todoapp.presentation.screens.list

import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

data class ListState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val action: Action = Action.NO_ACTION,
    val tasks: List<ToDoTaskEntity> = emptyList(),
    val searchBarQuery: String = "",
    val taskSelected: ToDoTaskEntity? = null,
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    val idTask: Int = 0,
    val titleTask: String = "",
    val description: String = "",
    val priority: Priority = Priority.NONE,
    val sortState: Priority = Priority.NONE
)
