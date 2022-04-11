package com.kunize.uswtimetable.ui.repository.signup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpRepository(private val dataSource: SignUpRemoteDataSource) {
    suspend fun checkId(id: String) = withContext(Dispatchers.IO) { dataSource.checkId(id) }
    suspend fun checkEmail(email: String) = withContext(Dispatchers.IO) { dataSource.checkEmail(email) }
    suspend fun signUp(id: String, pw: String, email: String) = withContext(Dispatchers.IO) { dataSource.signup(id, pw, email) }
}