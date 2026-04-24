package com.creative.androidfundamentalsbydantech.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class AppSettings(
    val darkMode: Boolean = false,
    val username: String = "",
)

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val keyDarkMode = booleanPreferencesKey("dark_mode")
    private val keyUsername = stringPreferencesKey("username")

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            darkMode = prefs[keyDarkMode] ?: false,
            username = prefs[keyUsername].orEmpty(),
        )
    }

    suspend fun setDarkMode(value: Boolean) {
        context.settingsDataStore.edit { it[keyDarkMode] = value }
    }

    suspend fun setUsername(value: String) {
        context.settingsDataStore.edit { it[keyUsername] = value }
    }
}
