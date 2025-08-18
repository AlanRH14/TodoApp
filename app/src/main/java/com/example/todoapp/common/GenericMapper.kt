package com.example.todoapp.common

interface GenericMapper<Entity, Domain> {
    fun mapToDomain(entity: Entity): Domain
}