package com.example.todoapp.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.todoapp.common.PreferencesKey
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.util.Constants.PREFERENCE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override suspend fun <T> saveState(key: PreferencesKey<T>, value: T) {
        TODO("Not yet implemented")
    }

    override fun <T> readSate(key: PreferencesKey<T>): Flow<T> {
        TODO("Not yet implemented")
    }

    private object PreferenceKey {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }

    suspend fun persistStore(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKey.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferenceKey.sortKey] ?: Priority.NONE.name
        }
}