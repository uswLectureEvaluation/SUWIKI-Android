package com.suwiki.local.user.datasource

import androidx.datastore.core.DataStore
import com.suwiki.core.model.user.DEFAULT_USER_EMAIL
import com.suwiki.core.model.user.DEFAULT_USER_ID
import com.suwiki.core.model.user.DEFAULT_USER_POINT
import com.suwiki.core.model.user.DEFAULT_USER_VIEW_EXAM
import com.suwiki.core.model.user.DEFAULT_USER_WRITTEN_EVALUATION
import com.suwiki.core.model.user.DEFAULT_USER_WRITTEN_EXAM
import com.suwiki.core.model.user.User
import com.suwiki.data.user.datasource.LocalUserDataSource
import com.suwiki.local.common.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
  private val dataStore: DataStore<UserPreference>,
) : LocalUserDataSource {

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

  override suspend fun clearUserInfo() {
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
