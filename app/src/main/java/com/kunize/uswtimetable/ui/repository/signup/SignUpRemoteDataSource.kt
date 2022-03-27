package com.kunize.uswtimetable.ui.repository.signup

import com.kunize.uswtimetable.dataclass.*
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class SignUpRemoteDataSource(private val apiService: IRetrofit) : SignUpDataSource {
    override suspend fun checkId(id: String): Response<OverlapCheckDto> = apiService.checkId(CheckIdFormat(id))

    override suspend fun checkEmail(email: String): Response<OverlapCheckDto> = apiService.checkEmail(CheckEmailFormat(email))

    override suspend fun signup(id: String, pw: String, email: String): Response<SuccessCheckDto> = apiService.signUp(SignUpFormat(id, pw, email))
}