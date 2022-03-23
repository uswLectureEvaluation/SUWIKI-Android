package com.kunize.uswtimetable.ui.repository.signup

import com.kunize.uswtimetable.dataclass.OverlapCheckDto
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import retrofit2.Call

interface SignUpDataSource {
    fun checkId(id: String): Call<OverlapCheckDto>
    fun checkEmail(email: String): Call<OverlapCheckDto>
    fun signup(id: String, pw: String, email: String): Call<SuccessCheckDto>
}