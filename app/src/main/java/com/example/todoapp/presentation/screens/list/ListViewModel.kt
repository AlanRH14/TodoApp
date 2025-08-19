package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.presentation.mvi.ListEffect
import com.example.todoapp.presentation.mvi.ListState
import com.example.todoapp.presentation.mvi.ListUIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel(
    private val repository: ToDoRepository,
    private val datStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ListEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ListUIEvent) {
        when (event) {
            is ListUIEvent.GetTasks -> {}
            is ListUIEvent.OnSearchTextUpdate -> {}
            is ListUIEvent.OnSnackBarActionClicked -> {}
            is ListUIEvent.OnSortTasksClicked -> {}
            is ListUIEvent.OnSearchKeyAction -> {}
            is ListUIEvent.OnSearchBarActionClicked -> {}
            is ListUIEvent.OnSwipeToDelete -> {}
            is ListUIEvent.OnReadSortState -> {}
            is ListUIEvent.OnActionUpdate -> {}
            is ListUIEvent.OnNavigateToTaskScreen -> {}

            else -> Unit
        }
    }
}