package com.example.todoapp.di

import com.example.todoapp.data.repositories.DataStoreRepositoryImpl
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.domain.repository.DataStoreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ToDoRepository> {
        ToDoRepository(get())
    }

    single<DataStoreRepository> { DataStoreRepositoryImpl(dataStore = get()) }
}