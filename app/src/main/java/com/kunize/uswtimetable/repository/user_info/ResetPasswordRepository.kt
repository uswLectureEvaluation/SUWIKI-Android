package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.ResetPasswordDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResetPasswordRepository {
    suspend fun resetPassword(current: String, new: String) =
        withContext(Dispatchers.IO) {
            IRetrofit.getInstance().resetPassword(ResetPasswordDto(
                currentPassword = current,
                newPassword = new
            ))
        }
}
