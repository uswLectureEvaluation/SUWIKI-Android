package com.suwiki.local.user.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.suwiki.data.datasource.local.LocalUserStorageDataSource
import com.suwiki.local.user.EMAIL
import com.suwiki.local.user.LOGGED_IN
import com.suwiki.local.user.POINT
import com.suwiki.local.user.USER_ID
import com.suwiki.local.user.VIEW_EXAM
import com.suwiki.local.user.WRITTEN_EVALUATION
import com.suwiki.local.user.WRITTEN_EXAM
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
}
