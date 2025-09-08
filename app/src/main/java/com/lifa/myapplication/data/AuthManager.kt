package com.lifa.myapplication.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lifa.myapplication.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "auth_prefs"

val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)

class AuthManager(private val context: Context) {

    private val KEY_TOKEN: Preferences.Key<String> = stringPreferencesKey("auth_token")
    private val KEY_USER_EMAIL: Preferences.Key<String> = stringPreferencesKey("user_email")

    val authTokenFlow: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val userEmailFlow: Flow<String?> = context.dataStore.data.map { it[KEY_USER_EMAIL] }

    suspend fun saveLogin(token: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_EMAIL] = email
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }


    suspend fun getUserInfo(): User? {
        return context.dataStore.data.map {
            User(
                it[KEY_USER_EMAIL] ?: "",
                0,
                it[KEY_TOKEN] ?: ""
            )
        }.firstOrNull()
    }

}



