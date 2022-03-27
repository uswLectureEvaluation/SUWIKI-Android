package com.kunize.uswtimetable.ui.repository.mypage

import com.kunize.uswtimetable.dataclass.UserDataDto
import retrofit2.Response

interface MyPageDataSource {
    suspend fun getUserData(): Response<UserDataDto>
}