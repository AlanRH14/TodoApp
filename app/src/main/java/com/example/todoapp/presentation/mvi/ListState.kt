package com.example.todoapp.presentation.mvi

import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.PriorityEntity
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

data class ListState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val action: Action = Action.NO_ACTION,
    val tasks: List<ToDoTaskEntity> = emptyList(),
    val searchBarState: String = "",
    val taskSelected: ToDoTaskEntity? = null,
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    val idTask: Int = 0,
    val titleTask: String = "",
    val description: String = "",
    val priorityEntity: PriorityEntity = PriorityEntity.NONE,
    val sortState: PriorityEntity = PriorityEntity.NONE,
    val searchTasks: List<ToDoTaskEntity> = emptyList()
)