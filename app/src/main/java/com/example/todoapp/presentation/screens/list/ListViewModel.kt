package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ListEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ListUIEvent) {
        when (event) {
            is ListUIEvent.GetTasks -> getTasks(priority = event.priority)

        }
    }

    private fun getTasks(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTasksByPriority(sortTasks = priority).collect { tasks ->
                when (tasks) {
                    is RequestState.Success -> {
                        _state.update { it.copy(tasks = tasks.data) }
                    }

                    else -> Unit
                }
            }
        }
    }
}