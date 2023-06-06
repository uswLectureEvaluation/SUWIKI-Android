package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.EmailDto
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class FindIdRepository @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) {
    suspend fun findId(email: String) =
        apiService.findId(EmailDto(email))
}
