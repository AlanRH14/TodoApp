package com.example.todoapp.data.preferences

import androidx.datastore.preferences.core.Preferences
import com.example.todoapp.common.PreferencesKey

internal sealed interface ConstantsPreferences<T>: PreferencesKey<T> {

    data object PriorityPreferences: ConstantsPreferences<String> {
        override val key: String =
        override val default: String
            get() = TODO("Not yet implemented")
        override val preferencesKey: Preferences.Key<String>
            get() = TODO("Not yet implemented")
    }
}