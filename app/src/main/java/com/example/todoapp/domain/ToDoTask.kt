package com.example.todoapp.domain

import com.example.todoapp.data.model.Priority

data class ToDoTask(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority
)