package com.example.todoapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class ToDoTaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val priority: Priority? = null
)