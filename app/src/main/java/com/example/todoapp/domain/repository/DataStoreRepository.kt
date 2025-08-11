package com.example.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun <T> saveState(key: String, value: T)

    fun <T> readSate(key: String): Flow<T>
}