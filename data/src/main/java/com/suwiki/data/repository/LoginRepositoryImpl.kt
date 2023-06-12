package com.suwiki.data.repository

import com.suwiki.data.datasource.LoginDataSource
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userInfoDataSource: LoginDataSource,
) : LoginRepository {

    override suspend fun login(id: String, pw: String): Result<Unit> =
        userInfoDataSource.login(id, pw)
}
