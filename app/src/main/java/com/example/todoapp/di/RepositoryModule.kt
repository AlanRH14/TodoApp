package com.example.todoapp.di

import com.example.todoapp.data.repositories.ToDoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ToDoRepository> {
        ToDoRepository(get())
    }
}