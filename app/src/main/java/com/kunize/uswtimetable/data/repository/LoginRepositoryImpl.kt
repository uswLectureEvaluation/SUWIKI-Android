package com.kunize.uswtimetable.data.repository

import com.kunize.uswtimetable.data.datasource.LoginDataSource
import com.kunize.uswtimetable.domain.model.Result
import com.kunize.uswtimetable.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userInfoDataSource: LoginDataSource,
) : LoginRepository {

    override suspend fun login(id: String, pw: String): Result<Unit> =
        userInfoDataSource.login(id, pw)
}
