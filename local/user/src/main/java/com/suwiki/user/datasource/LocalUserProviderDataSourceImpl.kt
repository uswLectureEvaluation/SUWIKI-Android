package com.suwiki.user.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.suwiki.data.datasource.local.LocalUserProviderDataSource
import com.suwiki.user.EMAIL
import com.suwiki.user.LOGGED_IN
import com.suwiki.user.POINT
import com.suwiki.user.USER_ID
import com.suwiki.user.VIEW_EXAM
import com.suwiki.user.WRITTEN_EVALUATION
import com.suwiki.user.WRITTEN_EXAM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserProviderDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalUserProviderDataSource {
    private val data: Flow<Preferences>
        get() = dataStore.data

    override val isLoggedIn: Flow<Boolean> = data.map { it[LOGGED_IN] ?: false }
    override val userId: Flow<String?> = data.map { it[USER_ID] }
    override val point: Flow<Int?> = data.map { it[POINT] }
    override val writtenEvaluation: Flow<Int?> = data.map { it[WRITTEN_EVALUATION] }
    override val writtenExam: Flow<Int?> = data.map { it[WRITTEN_EXAM] }
    override val viewExam: Flow<Int?> = data.map { it[VIEW_EXAM] }
    override val email: Flow<String?> = data.map { it[EMAIL] }
}
