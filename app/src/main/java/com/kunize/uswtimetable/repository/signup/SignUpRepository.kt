package com.kunize.uswtimetable.repository.signup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val dataSource: SignUpDataSource,
) {
    suspend fun checkId(id: String) = withContext(Dispatchers.IO) { dataSource.checkId(id) }
    suspend fun checkEmail(email: String) =
        withContext(Dispatchers.IO) { dataSource.checkEmail(email) }

    suspend fun signUp(id: String, pw: String, email: String) =
        withContext(Dispatchers.IO) { dataSource.signup(id, pw, email) }
}
