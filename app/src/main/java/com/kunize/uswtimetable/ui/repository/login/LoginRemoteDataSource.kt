package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.retrofit.IRetrofit

class LoginRemoteDataSource(private val apiService: IRetrofit) : LoginDataSource {

    override suspend fun login(userId: String, userPassword: String) = apiService.login(
        LoginIdPassword(userId, userPassword)
    )

    override suspend fun getUserData(accessToken: String) = apiService.getUserData(accessToken)
}