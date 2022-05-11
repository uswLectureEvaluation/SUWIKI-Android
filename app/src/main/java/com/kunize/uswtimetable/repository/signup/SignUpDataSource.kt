package com.kunize.uswtimetable.repository.signup

import com.kunize.uswtimetable.dataclass.OverlapCheckDto
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import retrofit2.Response

interface SignUpDataSource {
    suspend fun checkId(id: String): Response<OverlapCheckDto>
    suspend fun checkEmail(email: String): Response<OverlapCheckDto>
    suspend fun signup(id: String, pw: String, email: String): Response<SuccessCheckDto>
}