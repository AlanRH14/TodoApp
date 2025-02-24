package com.example.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.util.RequestState
import com.example.todoapp.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _searchAppBarState = MutableStateFlow(SearchAppBarState.CLOSED)
    val searchAppBarState: StateFlow<SearchAppBarState> get() = _searchAppBarState
    private val _searchTextAppBarState = MutableStateFlow("")
    val searchTextAppBarState: StateFlow<String> get() = _searchTextAppBarState
    private val _allTask = MutableStateFlow<List<ToDoTask>>(emptyList())
    val allTask: StateFlow<List<ToDoTask>> get() = _allTask
    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> get() = _selectedTask

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { tasks ->
                when (tasks) {
                    is RequestState.Success -> {
                        _allTask.value = tasks.data
                    }

                    else -> Unit
                }
            }
        }
    }

    fun setSearchAppBarState(searchAppBarState: SearchAppBarState) {
        _searchAppBarState.value = searchAppBarState
    }

    fun setSearchTextAppBarState(searchTextAppBarState: String) {
        _searchTextAppBarState.value = searchTextAppBarState
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { toDoTask ->
                _selectedTask.value = toDoTask
            }
        }
    }
}