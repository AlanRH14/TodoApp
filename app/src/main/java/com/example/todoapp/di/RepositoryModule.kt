package com.example.todoapp.di

import com.example.todoapp.data.local.preferences.DataStoreRepositoryImpl
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<ToDoRepository> {
        ToDoRepositoryImpl(
            toDoDao = get(),
            entityMapper = get(named("ToDoTaskEntityMapperImpl")),
            domainMapper = get(named("ToDoTaskDomainMapperImpl"))
        )
    }

    single<DataStoreRepository> { DataStoreRepositoryImpl(dataStore = get()) }
}