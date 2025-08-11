package com.example.todoapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)