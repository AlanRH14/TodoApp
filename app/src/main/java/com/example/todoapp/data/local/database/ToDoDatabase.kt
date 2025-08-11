package com.example.todoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.local.database.dao.ToDoDao

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao
}