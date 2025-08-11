package com.example.todoapp.data.local.mock_data

import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.local.database.ToDoTask

object TaskProvider {

    val taskItemTest = ToDoTask(
        id = 0,
        title = "Title",
        description = "Some random text",
        priority = Priority.MEDIUM
    )
}