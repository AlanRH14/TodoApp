package com.example.todoapp.presentation.mvi

import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.PriorityEntity
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

sealed interface ListUIEvent {
    data class GetTasks(val priorityEntity: PriorityEntity) : ListUIEvent
    data class OnSearchTextUpdate(val searchText: String) : ListUIEvent
    data class OnSnackBarActionClicked(val action: Action) : ListUIEvent
    data class OnSortTasksClicked(val priorityEntity: PriorityEntity) : ListUIEvent
    data object OnSearchKeyAction : ListUIEvent
    data class OnSearchBarActionClicked(val action: SearchAppBarState) : ListUIEvent
    data class OnSwipeToDelete(val action: Action, val taskSelected: ToDoTaskEntity?) : ListUIEvent
    data class OnActionUpdate(val action: Action): ListUIEvent

    data class OnGetTaskSelected(val taskID: Int) : ListUIEvent
    data class OnTaskFieldsUpdate(val taskSelected: ToDoTaskEntity?) : ListUIEvent
    data class OnNavigateToListScreen(val action: Action) : ListUIEvent
    data class OnTaskTitleUpdate(val taskTile: String) : ListUIEvent
    data class OnDescriptionUpdate(val description: String) : ListUIEvent
    data class OnPriorityUpdate(val priorityEntity: PriorityEntity) : ListUIEvent
    data object OnReadSortState : ListUIEvent
}