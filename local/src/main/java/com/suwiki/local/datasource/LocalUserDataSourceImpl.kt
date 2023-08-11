package com.suwiki.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.suwiki.data.datasource.local.LocalUserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalUserDataSource {
    private val data: Flow<Preferences>
        get() = dataStore.data

    override val isLoggedIn: Flow<Boolean> = data.map { it[LOGGED_IN] ?: false }
    override val userId: Flow<String?> = data.map { it[USER_ID] }
    override val point: Flow<Int?> = data.map { it[POINT] }
    override val writtenEvaluation: Flow<Int?> = data.map { it[WRITTEN_EVALUATION] }
    override val writtenExam: Flow<Int?> = data.map { it[WRITTEN_EXAM] }
    override val viewExam: Flow<Int?> = data.map { it[VIEW_EXAM] }
    override val email: Flow<String?> = data.map { it[EMAIL] }

    override suspend fun login(
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

    override suspend fun logout() {
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
