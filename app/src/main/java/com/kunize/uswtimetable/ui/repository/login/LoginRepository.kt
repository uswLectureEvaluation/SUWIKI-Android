package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.TimeTableSelPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val dataSource: LoginRemoteDataSource) {

    suspend fun login(id: String, pw: String) =
        withContext(Dispatchers.IO) {
            val result = dataSource.login(id, pw)
            result.body()?.also { tokens ->
                TimeTableSelPref.encryptedPrefs.saveAccessToken(tokens.accessToken)
                TimeTableSelPref.encryptedPrefs.saveRefreshToken(tokens.refreshToken)
            }
            return@withContext result
        }

    suspend fun getUserData() = withContext(Dispatchers.IO) { return@withContext dataSource.getUserData() }
}