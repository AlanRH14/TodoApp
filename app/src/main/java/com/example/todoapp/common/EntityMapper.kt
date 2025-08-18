package com.example.todoapp.common

interface EntityMapper<Entity, Domain> {
    fun mapToDomain(entity: Entity): Domain
}