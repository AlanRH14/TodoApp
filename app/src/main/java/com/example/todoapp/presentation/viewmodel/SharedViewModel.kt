package com.example.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.local.preferences.ConstantsPreferences
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.presentation.screens.list.ListEffect
import com.example.todoapp.presentation.screens.list.ListState
import com.example.todoapp.presentation.screens.list.ListUIEvent
import com.example.todoapp.util.Action
import com.example.todoapp.util.Constants
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

class SharedViewModel(
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

            is ListUIEvent.OnSearchTextUpdate -> onSearchTextUpdate(searchBar = event.searchText)

            is ListUIEvent.OnSnackBarActionClicked -> handleDatabaseActions(action = event.action)

            is ListUIEvent.OnSortTasksClicked -> {
                saveSortState(priority = event.priority)
                getTasks(priority = event.priority)
            }

            is ListUIEvent.OnSearchKeyAction -> searchTask()

            is ListUIEvent.OnSearchBarActionClicked -> setSearchAppBarState(searchAppBarState = event.action)

            is ListUIEvent.OnActionUpdate -> onActionUpdate(action = event.action)

            is ListUIEvent.OnReadSortState -> readSortState()

            is ListUIEvent.OnGetTaskSelected -> getSelectedTask(taskID = event.taskID)
            is ListUIEvent.OnTaskFieldsUpdate -> updateTaskFields(taskSelected = event.taskSelected)
            is ListUIEvent.OnNavigateToListScreen -> navigateToListScreen(action = event.action)
            is ListUIEvent.OnTaskTitleUpdate -> onTitleUpdate(title = event.taskTile)
            is ListUIEvent.OnDescriptionUpdate -> onDescriptionUpdate(event.description)
            is ListUIEvent.OnPriorityUpdate -> onPriorityUpdate(priority = event.priority)

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

    private fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> addTask(
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            Action.UPDATE -> updateTask(
                id = _state.value.idTask,
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            Action.DELETE -> deleteTask(
                id = _state.value.idTask,
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            Action.DELETE_ALL -> deleteAllTask()

            Action.UNDO -> addTask(
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            else -> Unit
        }
    }

    private fun searchTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchTask(searchQuery = "%${_state.value.searchBarState}%")
                .collect { searchTasks ->
                    when (searchTasks) {
                        is RequestState.Success -> {
                            _state.update { it.copy(searchTasks = searchTasks.data) }
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun addTask(
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTaskEntity(
                title = title,
                description = description,
                priority = priority
            )

            repository.addTask(toDoTaskEntity = toDoTask)
        }
    }

    private fun updateTask(
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTaskEntity(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            repository.updateTask(toDoTaskEntity = toDoTask)
        }
    }

    private fun deleteTask(
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTaskEntity(
                id = id,
                title = title,
                description = description,
                priority = priority,
            )

            repository.deleteTask(toDoTaskEntity = toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    private fun updateTaskFields(taskSelected: ToDoTaskEntity?) {
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
                    priority = Priority.LOW
                )
            }
        }
    }

    private fun getSelectedTask(taskID: Int) {
        if (taskID > -1) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getSelectedTask(taskId = taskID).collect { task ->
                    _state.update { it.copy(taskSelected = task) }
                }
            }
        }
    }

    private fun setSearchAppBarState(searchAppBarState: SearchAppBarState) {
        _state.update { it.copy(searchAppBarState = searchAppBarState) }
    }

    private fun onSearchTextUpdate(searchBar: String) {
        _state.update { it.copy(searchBarState = searchBar) }
    }

    private fun onTitleUpdate(title: String) {
        if (title.length < Constants.MAX_TITLE_LENGTH) {
            _state.update { it.copy(titleTask = title) }
        }
    }

    private fun onDescriptionUpdate(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun onPriorityUpdate(priority: Priority) {
        _state.update { it.copy(priority = priority) }
    }

    private fun validateFields(): Boolean {
        return _state.value.titleTask.isNotEmpty() && _state.value.description.isNotEmpty()
    }

    private fun onActionUpdate(action: Action) {
        _state.update { it.copy(action = action) }
    }

    private fun saveSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveState(
                key = ConstantsPreferences.PriorityPreferences,
                value = priority.name
            )
        }
    }

    private fun readSortState() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.readSate(key = ConstantsPreferences.PriorityPreferences)
                .map { Priority.valueOf(it) }
                .collect { priority ->
                    _state.update { it.copy(sortState = priority) }
                }
        }
    }

    private fun navigateToListScreen(action: Action) {
        viewModelScope.launch {
            if (action == Action.NO_ACTION) {
                handleDatabaseActions(action = action)
                _effect.emit(ListEffect.NavigateToListScreen(action = action))
            } else {
                if (validateFields()) {
                    handleDatabaseActions(action = action)
                    _effect.emit(ListEffect.NavigateToListScreen(action = action))
                } else {
                    _effect.emit(ListEffect.ShowMessage(message = "Fields Empty."))
                }
            }
        }
    }
}