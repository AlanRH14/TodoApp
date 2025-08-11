package com.example.todoapp.di

import com.example.todoapp.data.repositories.DataRepositoryImpl
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.domain.repository.DataStoreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ToDoRepository> {
        ToDoRepository(get())
    }

    single<DataStoreRepository> { DataRepositoryImpl(dataStore = get()) }
}