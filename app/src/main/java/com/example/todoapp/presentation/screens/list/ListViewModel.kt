package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ListEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ListUIEvent) {

    }
}