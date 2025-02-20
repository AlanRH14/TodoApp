package com.example.todoapp.data.mock_data

import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.ToDoTask

object TaskProvider {

    val taskItemTest = ToDoTask(
        id = 0,
        title = "Title",
        description = "Some random text",
        priority = Priority.MEDIUM
    )
}