package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.domain.di.AuthApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import javax.inject.Inject

class QuitRepository @Inject constructor(
    @AuthApiService private val apiService: IRetrofit,
) {

    suspend fun quit(id: String, password: String) =
        apiService.quit(
            LoginIdPassword(
                loginId = id,
                password = password,
            ),
        )
}
