package com.example.todoapp.di

import com.example.todoapp.common.GenericMapper
import com.example.todoapp.data.mapper_impl.ToDoTaskDomainMapperImpl
import com.example.todoapp.data.mapper_impl.ToDoTaskEntityMapperImpl
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.domain.ToDoTask
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    single<GenericMapper<ToDoTaskEntity, ToDoTask>>(named("ToDoTaskEntityMapperImpl")) {
        ToDoTaskEntityMapperImpl()
    }

    single<GenericMapper<ToDoTask, ToDoTaskEntity>>(named("ToDoTaskDomainMapperImpl")) {
        ToDoTaskDomainMapperImpl()
    }
}