package com.kunize.uswtimetable.ui.repository.user_info

import com.kunize.uswtimetable.dataclass.ResetPasswordDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResetPasswordRepository {
    suspend fun resetPassword(current: String, new: String) =
        // TODO 현재 비밀번호를 받는 API 로 변경 시 이 부분도 변경해야 함
        withContext(Dispatchers.IO) {
            IRetrofit.getInstance().resetPassword(ResetPasswordDto(
                newPassword = new
            ))
        }
}
