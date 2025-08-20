package com.example.todoapp.presentation.screens.list.mvi

import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

sealed interface ListUIEvent {
    data class GetTasks(val priority: Priority) : ListUIEvent
    data class OnSearchTextUpdate(val searchText: String) : ListUIEvent
    data class OnSnackBarActionClicked(val action: Action) : ListUIEvent
    data class OnSortTasksClicked(val priority: Priority) : ListUIEvent
    data object OnSearchKeyAction : ListUIEvent
    data class OnSearchBarActionClicked(val action: SearchAppBarState) : ListUIEvent
    data class OnSwipeToDelete(val action: Action, val taskSelected: ToDoTask?) : ListUIEvent
    data class OnActionUpdate(val action: Action): ListUIEvent
    data class OnNavigateToTaskScreen(val taskID: Int): ListUIEvent
    data object OnReadSortState : ListUIEvent
}