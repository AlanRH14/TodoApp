package com.example.todoapp.domain.repository

import com.example.todoapp.common.PreferencesKey
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun <T> saveState(key: PreferencesKey<T>, value: T)

    fun <T> readSate(key: PreferencesKey<T>): Flow<T>
}