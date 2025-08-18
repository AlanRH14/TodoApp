package com.example.todoapp.domain

data class ToDoTask(
    val id: Int,
    val title: String,
    val description: String,
    val priority: String
)