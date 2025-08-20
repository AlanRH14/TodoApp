package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.preferences.ConstantsPreferences
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.presentation.mvi.ListEffect
import com.example.todoapp.presentation.mvi.ListState
import com.example.todoapp.presentation.mvi.ListUIEvent
import com.example.todoapp.util.Action
import com.example.todoapp.util.RequestState
import com.example.todoapp.util.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            is ListUIEvent.GetTasks -> getTasks(priority = event.priority)
            is ListUIEvent.OnSearchTextUpdate -> onSearchTextUpdate(searchText = event.searchText)
            is ListUIEvent.OnSnackBarActionClicked -> {
                onActionUpdate(action = event.action)
                handleDatabaseActions(action = event.action)
            }
            is ListUIEvent.OnSortTasksClicked -> {
                saveSortState(priority = event.priority)
                getTasks(priority = event.priority)
            }
            is ListUIEvent.OnSearchKeyAction -> searchTask()
            is ListUIEvent.OnSearchBarActionClicked -> setSearchAppBarState(searchAppBarState = event.action)
            is ListUIEvent.OnSwipeToDelete -> {
                onActionUpdate(action = event.action)
                handleDatabaseActions(action = event.action)
                updateTaskSelected(taskSelected = event.taskSelected)
            }
            is ListUIEvent.OnReadSortState -> readSortState()
            is ListUIEvent.OnActionUpdate -> onActionUpdate(action = event.action)
            is ListUIEvent.OnNavigateToTaskScreen -> navigationToTaskScreen(taskID = event.taskID)

            else -> Unit
        }
    }

    private fun getTasks(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTasksByPriority(sortTasks = priority).collect { task ->
                when (task) {
                    is RequestState.Success -> {
                        _state.update { it.copy(tasks = task.data) }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> addTask(
                toDoTask = ToDoTask(
                    title = _state.value.titleTask,
                    description = _state.value.description,
                    priority = _state.value.priority
                )
            )

            Action.UPDATE -> updateTask(
                toDoTask = ToDoTask(
                    id = _state.value.idTask,
                    title = _state.value.titleTask,
                    description = _state.value.description,
                    priority = _state.value.priority
                )
            )

            Action.DELETE -> deleteTask(
                toDoTask = ToDoTask(
                    id = _state.value.idTask,
                    title = _state.value.titleTask,
                    description = _state.value.description,
                    priority = _state.value.priority
                )
            )

            Action.DELETE_ALL -> deleteAllTasks()

            Action.UNDO -> addTask(
                toDoTask = ToDoTask(
                    title = _state.value.titleTask,
                    description = _state.value.description,
                    priority = _state.value.priority
                )
            )

            else -> Unit
        }
    }

    private fun searchTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchTask(searchQuery = "%${_state.value.searchText}%")
                .collect { searchTasks ->
                    when (searchTasks) {
                        is RequestState.Success -> {
                            _state.update { it.copy(tasks = searchTasks.data) }
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun addTask(toDoTask: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(toDoTask = toDoTask)
        }
    }

    private fun updateTask(toDoTask: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask(toDoTask: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    private fun getSelectTask(taskID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSelectedTask(taskId = taskID).collect { task ->
                _state.update { it.copy(taskSelected = task) }
            }
        }
    }

    private fun setSearchAppBarState(searchAppBarState: SearchAppBarState) {
        _state.update { it.copy(searchAppBarState = searchAppBarState) }
    }

    private fun onSearchTextUpdate(searchText: String) {
        _state.update { it.copy(searchText = searchText) }
    }

    private fun onActionUpdate(action: Action) {
        _state.update { it.copy(action = action) }
    }

    private fun saveSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            datStoreRepository.saveState(
                key = ConstantsPreferences.PriorityPreferences,
                value = priority.name
            )
        }
    }

    private fun readSortState() {
        viewModelScope.launch(Dispatchers.IO) {
            datStoreRepository.readSate(key = ConstantsPreferences.PriorityPreferences)
                .map { Priority.valueOf(it) }
                .collect { priority ->
                    _state.update { it.copy(priority = priority) }
                }
        }
    }

    private fun navigationToTaskScreen(taskID: Int) {
        viewModelScope.launch {
            _effect.emit(ListEffect.NavigateToTaskScreen(taskID = taskID))
        }
    }

    private fun updateTaskSelected(taskSelected: ToDoTask?) {
        if (taskSelected != null) {
            _state.update {
                it.copy(
                    idTask = taskSelected.id,
                    titleTask = taskSelected.title,
                    description = taskSelected.description,
                    priority = taskSelected.priority,
                )
            }
        } else {
            _state.update {
                it.copy(
                    idTask = 0,
                    titleTask = "",
                    description = "",
                    priority = Priority.NONE,
                )
            }
        }
    }
}