package com.kunize.uswtimetable.ui.repository.signup

import com.kunize.uswtimetable.dataclass.*
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Call

class SignUpRemoteDataSource : SignUpDataSource {

    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }

    override fun checkId(id: String): Call<OverlapCheckDto> = retrofit.checkId(CheckIdFormat(id))

    override fun checkEmail(email: String): Call<OverlapCheckDto> = retrofit.checkEmail(
        CheckEmailFormat(email)
    )

    override fun signup(id: String, pw: String, email: String): Call<SuccessCheckDto> = retrofit.signUp(SignUpFormat(id, pw, email))
}