package com.example.todoapp.di

import androidx.room.Room
import com.example.todoapp.data.local.database.ToDoDatabase
import com.example.todoapp.util.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single { get<ToDoDatabase>().toDoDao() }
}