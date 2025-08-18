package com.example.todoapp.presentation.mvi

import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

data class ListState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val action: Action = Action.NO_ACTION,
    val tasks: List<ToDoTask> = emptyList(),
    val searchBarState: String = "",
    val taskSelected: ToDoTask? = null,
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    val idTask: Int = 0,
    val titleTask: String = "",
    val description: String = "",
    val priority: Priority = Priority.NONE,
    val sortState: Priority = Priority.NONE,
)