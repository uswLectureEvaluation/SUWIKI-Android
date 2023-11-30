package com.suwiki.local.user.datasource

import androidx.datastore.core.DataStore
import com.suwiki.core.database.UserPreference
import com.suwiki.core.model.user.User
import com.suwiki.data.user.datasource.LocalUserProviderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserProviderDataSourceImpl @Inject constructor(
  private val dataStore: DataStore<UserPreference>,
) : LocalUserProviderDataSource {

  override val user: Flow<User>
    get() = dataStore.data.map {
      User(
        userId = it.userId,
        email = it.email,
        point = it.point,
        writtenEvaluation = it.writtenEvaluation,
        writtenExam = it.writtenExam,
        viewExam = it.viewExam,
      )
    }
}
