package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.util.Result

interface LoginDataSource {
    suspend fun login(userId: String, userPassword: String): Result<LoggedInUser>
    suspend fun logout()
}