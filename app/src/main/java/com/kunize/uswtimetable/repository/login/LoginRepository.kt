package com.kunize.uswtimetable.repository.login

import com.kunize.uswtimetable.util.SuwikiApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource,
) {

    suspend fun login(id: String, pw: String) =
        withContext(Dispatchers.IO) {
            val result = dataSource.login(id, pw)
            result.body()?.also { tokens ->
                SuwikiApplication.encryptedPrefs.saveAccessToken(tokens.accessToken)
                SuwikiApplication.encryptedPrefs.saveRefreshToken(tokens.refreshToken)
            }
            return@withContext result
        }

    suspend fun getUserData() =
        withContext(Dispatchers.IO) { return@withContext dataSource.getUserData() }
}
