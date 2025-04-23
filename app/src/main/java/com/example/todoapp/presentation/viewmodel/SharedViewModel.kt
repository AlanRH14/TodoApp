package com.example.todoapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.data.repositories.DataRepository
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.util.Action
import com.example.todoapp.util.Constants.MAX_TITLE_LENGTH
import com.example.todoapp.util.RequestState
import com.example.todoapp.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataRepository
) : ViewModel() {

    private val _id = MutableStateFlow(0)
    private val _action = MutableStateFlow(Action.NO_ACTION)
    val action: StateFlow<Action> get() = _action
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description
    private val _priority = MutableStateFlow(Priority.NONE)
    val priority: StateFlow<Priority> get() = _priority
    private val _searchAppBarState = MutableStateFlow(SearchAppBarState.CLOSED)
    val searchAppBarState: StateFlow<SearchAppBarState> get() = _searchAppBarState
    private val _searchTextAppBarState = MutableStateFlow("")
    val searchTextAppBarState: StateFlow<String> get() = _searchTextAppBarState
    private val _tasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val tasks: StateFlow<List<ToDoTask>> get() = _tasks
    private val _searchTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val searchTasks: StateFlow<List<ToDoTask>> get() = _searchTasks
    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> get() = _selectedTask
    private val _sortState = MutableStateFlow(Priority.NONE)
    val sortState: StateFlow<Priority> get() = _sortState

    init {
        readSortState()
    }

    fun getTasks(sortState: Priority) {
        Log.d("getTasks", "TRIGGERED")
        when (sortState) {
            Priority.LOW -> {
                getLowPriorityTask()
            }
            Priority.HIGH -> {
                getHighPriorityTask()
            }
            else -> getAllTasks()
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { tasks ->
                when (tasks) {
                    is RequestState.Success -> {
                        _tasks.value = tasks.data
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getLowPriorityTask() {
        viewModelScope.launch {
            repository.sortByLowPriority().collect { toDoTask ->
                when (toDoTask) {
                    is RequestState.Success -> {
                        _tasks.value = toDoTask.data
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getHighPriorityTask() {
        viewModelScope.launch {
            repository.sortByHighPriority().collect { toDoTask ->
                when (toDoTask) {
                    is RequestState.Success -> {
                        _tasks.value = toDoTask.data
                    }

                    else -> Unit
                }
            }
        }
    }

    fun searchTasks(searchQuery: String) {
        viewModelScope.launch {
            repository.searchTask(searchQuery = "%$searchQuery%").collect { searchTasks ->
                when (searchTasks) {
                    is RequestState.Success -> {
                        _searchTasks.value = searchTasks.data
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                title = _title.value,
                description = _description.value,
                priority = _priority.value
            )

            repository.addTask(toDoTask = toDoTask)
        }
    }

    private fun updateTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                id = _id.value,
                title = _title.value,
                description = _description.value,
                priority = _priority.value
            )

            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                id = _id.value,
                title = _title.value,
                description = _description.value,
                priority = _priority.value
            )

            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTask()
            }

            Action.UNDO -> {
                addTask()
            }

            else -> Unit
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            _id.value = selectedTask.id
            _title.value = selectedTask.title
            _description.value = selectedTask.description
            _priority.value = selectedTask.priority
        } else {
            _id.value = 0
            _title.value = ""
            _description.value = ""
            _priority.value = Priority.LOW
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { toDoTask ->
                _selectedTask.value = toDoTask
            }
        }
    }

    fun setSearchAppBarState(searchAppBarState: SearchAppBarState) {
        _searchAppBarState.value = searchAppBarState
    }

    fun setSearchTextAppBarState(searchTextAppBarState: String) {
        _searchTextAppBarState.value = searchTextAppBarState
    }

    fun setTitleTask(title: String) {
        if (title.length < MAX_TITLE_LENGTH) {
            _title.value = title
        }
    }

    fun setDescriptionTask(description: String) {
        _description.value = description
    }

    fun setPriorityTask(priority: Priority) {
        _priority.value = priority
    }

    fun validateFields(): Boolean {
        return _title.value.isNotEmpty() && _description.value.isNotEmpty()
    }

    fun updateAction(action: Action) {
        _action.value = action
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch {
            dataStoreRepository.persistStore(priority = priority)
        }
    }

    private fun readSortState() {
        Log.d("readSortState", "TRIGGERED")
        viewModelScope.launch {
            dataStoreRepository.readSortState
                .map { Priority.valueOf(it) }
                .collect { priority ->
                    _sortState.value = priority
                }
        }
    }
}