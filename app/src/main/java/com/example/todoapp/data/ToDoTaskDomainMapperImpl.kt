package com.example.todoapp.data

import com.example.todoapp.common.GenericMapper
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.domain.ToDoTask

class ToDoTaskDomainMapperImpl : GenericMapper<ToDoTask, ToDoTaskEntity> {

    override fun mapToDomain(entity: ToDoTask): ToDoTaskEntity = ToDoTaskEntity(
        title = entity.title,
        description = entity.description,
        priority = entity.priority
    )
}