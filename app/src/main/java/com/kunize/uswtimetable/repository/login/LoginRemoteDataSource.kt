package com.kunize.uswtimetable.repository.login

import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) : LoginDataSource {

    override suspend fun login(userId: String, userPassword: String) =
        apiService.login(LoginIdPassword(userId, userPassword))

    override suspend fun getUserData() = apiService.getUserData()
}
