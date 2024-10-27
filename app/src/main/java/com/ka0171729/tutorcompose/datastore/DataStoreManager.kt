package com.ka0171729.tutorcompose.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    companion object {
        val USER_TOKEN = stringPreferencesKey("USER_TOKEN")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        val USER_AVATAR = stringPreferencesKey("USER_AVATAR")
        val USER_PHONE = stringPreferencesKey("USER_PHONE")
        val USER_ROLE = stringPreferencesKey("USER_ROLE")
        val USER_ID = intPreferencesKey("USER_ID")
    }


    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[USER_NAME] = user.name
            mutablePreferences[USER_EMAIL] = user.email
            mutablePreferences[USER_AVATAR] = user.avatar
            mutablePreferences[USER_PHONE] = user.phone
            mutablePreferences[USER_ROLE] = user.role
            mutablePreferences[USER_ID] = user.id
        }
    }


    val tokenFlow: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_TOKEN]
        }

    val userFlow: Flow<User?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            User(
                avatar = preferences[USER_AVATAR] ?: "",
                createdAt = "",
                email = preferences[USER_EMAIL] ?: "",
                emailVerifiedAt = "",
                id = preferences[USER_ID] ?: 0,
                name = preferences[USER_NAME] ?: "",
                phone = preferences[USER_PHONE] ?: "",
                role = preferences[USER_ROLE] ?: "",
                updatedAt = ""
            )
        }

    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}