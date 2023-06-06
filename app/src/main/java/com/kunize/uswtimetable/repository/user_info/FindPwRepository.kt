package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.UserIdEmail
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class FindPwRepository @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) {

    suspend fun findPw(id: String, email: String) = apiService.findPassword(UserIdEmail(id, email))
}
