package com.example.todoapp.data

import com.example.todoapp.common.EntityMapper
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.domain.ToDoTask

class ToDoTaskEntityMapperImpl: EntityMapper<ToDoTaskEntity, ToDoTask> {

    override fun mapToDomain(entity: ToDoTaskEntity): ToDoTask = ToDoTask(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        priority = entity.priority
    )
}