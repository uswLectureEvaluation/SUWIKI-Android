package com.kunize.uswtimetable.ui.repository.signup

class SignUpRepository(private val dataSource: SignUpRemoteDataSource) {
    suspend fun checkId(id: String) = dataSource.checkId(id)
    suspend fun checkEmail(email: String) = dataSource.checkEmail(email)
    suspend fun signUp(id: String, pw: String, email: String) = dataSource.signup(id, pw, email)
}