package com.example.todoapp.presentation.screens.list

import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.Action

sealed interface ListUIEvent {
    data class GetTasks(val priority: Priority) : ListUIEvent
    data class OnSearchTextUpdate(val searchText: String) : ListUIEvent
    data class OnClickActionSnackBar(val action: Action) : ListUIEvent
    data class OnSortTasksClicked(val priority: Priority) : ListUIEvent
    data object OnSearchKeyAction: ListUIEvent
}