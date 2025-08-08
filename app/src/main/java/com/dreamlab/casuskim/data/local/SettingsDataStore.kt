package com.dreamlab.casuskim.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    companion object {
        private val ROUND_TIME_KEY = intPreferencesKey("round_time_seconds")
    }

    suspend fun saveRoundTimeSeconds(seconds: Int) {
        context.dataStore.edit { prefs ->
            prefs[ROUND_TIME_KEY] = seconds
        }
    }

    fun getRoundTimeSeconds(): Flow<Int> {
        return context.dataStore.data.map { prefs ->
            prefs[ROUND_TIME_KEY] ?: 600
        }
    }
}