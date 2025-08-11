package com.example.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.local.database.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao
}