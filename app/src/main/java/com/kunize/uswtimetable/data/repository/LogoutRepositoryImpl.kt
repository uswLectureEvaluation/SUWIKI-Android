package com.kunize.uswtimetable.data.repository

import com.kunize.uswtimetable.data.datastore.UserPreference
import com.kunize.uswtimetable.domain.repository.LogoutRepository
import com.kunize.uswtimetable.SuwikiApplication
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val userPreference: UserPreference,
) : LogoutRepository {

    override suspend fun logout() {
        userPreference.logout()
        SuwikiApplication.encryptedPrefs.saveAccessToken("") // TODO DataStore 로 마이그레이션
        SuwikiApplication.encryptedPrefs.saveRefreshToken("")
    }
}
