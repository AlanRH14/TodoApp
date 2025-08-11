package com.example.todoapp.presentation.screens.list

import com.example.todoapp.data.model.Priority

sealed interface ListEffect {

    data class SortTasks(val priority: Priority): ListEffect
}