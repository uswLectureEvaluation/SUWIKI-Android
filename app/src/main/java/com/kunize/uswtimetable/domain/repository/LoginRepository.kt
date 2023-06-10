package com.kunize.uswtimetable.domain.repository

import com.kunize.uswtimetable.domain.model.Result

interface LoginRepository {
    suspend fun login(id: String, pw: String): Result<Unit>
}
