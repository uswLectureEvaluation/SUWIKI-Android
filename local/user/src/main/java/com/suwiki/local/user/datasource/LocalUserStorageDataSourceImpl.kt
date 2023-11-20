package com.suwiki.local.user.datasource

import androidx.datastore.core.DataStore
import com.suwiki.core.database.UserPreference
import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User
import com.suwiki.core.security.SecurityPreferences
import com.suwiki.data.user.datasource.LocalUserStorageDataSource
import javax.inject.Inject

class LocalUserStorageDataSourceImpl @Inject constructor(
  private val dataStore: DataStore<UserPreference>,
  private val securityPreferences: SecurityPreferences,
) : LocalUserStorageDataSource {

  override suspend fun login(
    user: User,
    token: Token,
  ) {
    token.run {
      securityPreferences.setAccessToken(accessToken)
      securityPreferences.setRefreshToken(refreshToken)
    }

    user.run {
      dataStore.updateData { userPref ->
        userPref
          .toBuilder()
          .setUserId(userId)
          .setEmail(email)
          .setPoint(point)
          .setWrittenEvaluation(writtenEvaluation)
          .setWrittenExam(writtenExam)
          .setViewExam(viewExam)
          .build()
      }
    }
  }

  override suspend fun logout() {
    securityPreferences.clearAll()
    dataStore.updateData { userPref ->
      userPref
        .toBuilder()
        .clear()
        .build()
    }
  }
}
