package com.example.todoapp.di

import com.example.todoapp.data.preferences.DataStoreRepositoryImpl
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import com.example.todoapp.domain.repository.DataStoreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ToDoRepositoryImpl> {
        ToDoRepositoryImpl(get())
    }

    single<DataStoreRepository> { DataStoreRepositoryImpl(dataStore = get()) }
}