package com.kunize.uswtimetable.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreference @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore: DataStore<Preferences>
        get() = context.dataStore
    private val data: Flow<Preferences>
        get() = dataStore.data

    val isLoggedIn: Flow<Boolean> = data.map { it[LOGGED_IN] ?: false }
    val userId: Flow<String?> = data.map { it[USER_ID] }
    val point: Flow<Int?> = data.map { it[POINT] }
    val writtenEvaluation: Flow<Int?> = data.map { it[WRITTEN_EVALUATION] }
    val writtenExam: Flow<Int?> = data.map { it[WRITTEN_EXAM] }
    val viewExam: Flow<Int?> = data.map { it[VIEW_EXAM] }
    val email: Flow<String?> = data.map { it[EMAIL] }

    suspend fun login(
        userId: String,
        point: Int,
        writtenEvaluation: Int,
        writtenExam: Int,
        viewExam: Int,
        email: String,
    ) {
        setUserInfo(userId, point, writtenEvaluation, writtenExam, viewExam, email)
        dataStore.edit { it[LOGGED_IN] = true }
    }

    suspend fun logout() {
        setUserInfo("", 0, 0, 0, 0, "")
        dataStore.edit { it[LOGGED_IN] = false }
    }

    private suspend fun setUserInfo(
        userId: String,
        point: Int,
        writtenEvaluation: Int,
        writtenExam: Int,
        viewExam: Int,
        email: String,
    ) {
        dataStore.edit {
            it[USER_ID] = userId
            it[POINT] = point
            it[WRITTEN_EVALUATION] = writtenEvaluation
            it[WRITTEN_EXAM] = writtenExam
            it[VIEW_EXAM] = viewExam
            it[EMAIL] = email
        }
    }

    companion object {
        val LOGGED_IN = booleanPreferencesKey("[KEY] is logged in")

        val USER_ID = stringPreferencesKey("[KEY] user id")
        val POINT = intPreferencesKey("[KEY] user point")
        val WRITTEN_EVALUATION = intPreferencesKey("[KEY]user written evaluation count")
        val WRITTEN_EXAM = intPreferencesKey("[KEY] user written exam count")
        val VIEW_EXAM = intPreferencesKey("[KEY] user exam view count")
        val EMAIL = stringPreferencesKey("[KEY] user email")
    }
}
