package com.example.todoapp.data.local.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.todoapp.common.PreferencesKey
import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.PreferencesConstants.PREFERENCE_KEY

internal sealed interface ConstantsPreferences<T>: PreferencesKey<T> {
    data object PriorityPreferences: ConstantsPreferences<String> {
        override val key: String = PREFERENCE_KEY
        override val default: String = Priority.NONE.name
        override val preferencesKey: Preferences.Key<String> = stringPreferencesKey(name = key)
    }
}