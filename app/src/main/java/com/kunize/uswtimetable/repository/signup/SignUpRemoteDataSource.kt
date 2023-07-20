package com.kunize.uswtimetable.repository.signup

import com.kunize.uswtimetable.dataclass.CheckEmailFormat
import com.kunize.uswtimetable.dataclass.CheckIdFormat
import com.kunize.uswtimetable.dataclass.OverlapCheckDto
import com.kunize.uswtimetable.dataclass.SignUpFormat
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response
import javax.inject.Inject

class SignUpRemoteDataSource @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) : SignUpDataSource {
    override suspend fun checkId(id: String): Response<OverlapCheckDto> =
        apiService.checkId(CheckIdFormat(id))

    override suspend fun checkEmail(email: String): Response<OverlapCheckDto> =
        apiService.checkEmail(CheckEmailFormat(email))

    override suspend fun signup(id: String, pw: String, email: String): Response<SuccessCheckDto> =
        apiService.signUp(SignUpFormat(id, pw, email))
}
