package com.suwiki.local.user.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.suwiki.data.user.datasource.LocalUserProviderDataSource
import com.suwiki.local.user.EMAIL
import com.suwiki.local.user.LOGGED_IN
import com.suwiki.local.user.POINT
import com.suwiki.local.user.USER_ID
import com.suwiki.local.user.VIEW_EXAM
import com.suwiki.local.user.WRITTEN_EVALUATION
import com.suwiki.local.user.WRITTEN_EXAM
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
