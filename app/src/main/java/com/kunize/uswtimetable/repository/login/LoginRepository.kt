package com.kunize.uswtimetable.repository.login

import com.kunize.uswtimetable.util.SuwikiApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val dataSource: LoginRemoteDataSource) {

    suspend fun login(id: String, pw: String) =
        withContext(Dispatchers.IO) {
            val result = dataSource.login(id, pw)
            result.body()?.also { tokens ->
                SuwikiApplication.encryptedPrefs.saveAccessToken(tokens.accessToken)
                SuwikiApplication.encryptedPrefs.saveRefreshToken(tokens.refreshToken)
            }
            return@withContext result
        }

    suspend fun getUserData() = withContext(Dispatchers.IO) { return@withContext dataSource.getUserData() }
}