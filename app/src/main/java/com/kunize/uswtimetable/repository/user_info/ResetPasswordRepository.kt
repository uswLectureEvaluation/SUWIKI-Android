package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.ResetPasswordDto
import com.kunize.uswtimetable.domain.di.AuthApiService
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetPasswordRepository @Inject constructor(
    @AuthApiService private val apiService: IRetrofit,
) {
    suspend fun resetPassword(current: String, new: String) =
        withContext(Dispatchers.IO) {
            apiService.resetPassword(
                ResetPasswordDto(
                    currentPassword = current,
                    newPassword = new,
                ),
            )
        }
}
