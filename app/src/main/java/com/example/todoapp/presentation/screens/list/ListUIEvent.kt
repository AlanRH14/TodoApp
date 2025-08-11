package com.example.todoapp.presentation.screens.list

import com.example.todoapp.data.model.Priority

sealed interface ListUIEvent {

    data class GetTasks(val priority: Priority) : ListUIEvent
}