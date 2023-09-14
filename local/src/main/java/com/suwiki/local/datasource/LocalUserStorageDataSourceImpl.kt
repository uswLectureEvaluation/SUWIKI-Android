package com.suwiki.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.suwiki.data.datasource.local.LocalUserStorageDataSource
import javax.inject.Inject

class LocalUserStorageDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalUserStorageDataSource {

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
