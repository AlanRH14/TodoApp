package com.example.todoapp.di

import com.example.todoapp.common.GenericMapper
import com.example.todoapp.data.ToDoTaskEntityMapperImpl
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.domain.ToDoTask
import org.koin.dsl.module

val mapperModule = module {
    single<GenericMapper<ToDoTaskEntity, ToDoTask>> {
        ToDoTaskEntityMapperImpl()
    }
}