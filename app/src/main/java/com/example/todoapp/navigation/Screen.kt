package com.example.todoapp.navigation

import com.example.todoapp.util.Action

sealed class Screen {
    data class List (val action:  Action): Screen()

    data class Task(val taskId: Int): Screen()
}