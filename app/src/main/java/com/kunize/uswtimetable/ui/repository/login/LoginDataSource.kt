package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.Token
import com.kunize.uswtimetable.dataclass.UserDataDto
import retrofit2.Response

interface LoginDataSource {
    suspend fun login(userId: String, userPassword: String): Response<Token>
    suspend fun getUserData(accessToken: String): Response<UserDataDto>
}