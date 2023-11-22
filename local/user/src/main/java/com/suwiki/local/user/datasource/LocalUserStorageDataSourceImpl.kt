package com.suwiki.local.user.datasource

import androidx.datastore.core.DataStore
import com.suwiki.core.database.UserPreference
import com.suwiki.core.model.user.DEFAULT_USER_EMAIL
import com.suwiki.core.model.user.DEFAULT_USER_ID
import com.suwiki.core.model.user.DEFAULT_USER_POINT
import com.suwiki.core.model.user.DEFAULT_USER_VIEW_EXAM
import com.suwiki.core.model.user.DEFAULT_USER_WRITTEN_EVALUATION
import com.suwiki.core.model.user.DEFAULT_USER_WRITTEN_EXAM
import com.suwiki.core.model.user.Token
import com.suwiki.core.model.user.User
import com.suwiki.core.security.SecurityPreferences
import com.suwiki.data.user.datasource.LocalUserStorageDataSource
import javax.inject.Inject

class LocalUserStorageDataSourceImpl @Inject constructor(
  private val dataStore: DataStore<UserPreference>,
  private val securityPreferences: SecurityPreferences,
) : LocalUserStorageDataSource {

  override suspend fun setUserInfo(user: User) {
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

  override suspend fun setToken(token: Token) {
    token.run {
      securityPreferences.setAccessToken(accessToken)
      securityPreferences.setRefreshToken(refreshToken)
    }
  }

  override suspend fun clearUserInfoAndToken() {
    securityPreferences.clearAll()
    dataStore.updateData { userPref ->
      userPref
        .toBuilder()
        .setUserId(DEFAULT_USER_ID)
        .setEmail(DEFAULT_USER_EMAIL)
        .setPoint(DEFAULT_USER_POINT)
        .setWrittenEvaluation(DEFAULT_USER_WRITTEN_EVALUATION)
        .setWrittenExam(DEFAULT_USER_WRITTEN_EXAM)
        .setViewExam(DEFAULT_USER_VIEW_EXAM)
        .build()
    }
  }
}
