package com.suwiki.data.repository

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : SignUpRepository {

    override suspend fun checkId(id: String): Result<Boolean> {
        return apiService.checkId(id).toResult().map { it.overlap }
    }

    override suspend fun checkEmail(email: String): Result<Boolean> {
        return apiService.checkEmail(email).toResult().map { it.overlap }
    }

    override suspend fun signUp(id: String, pw: String, email: String): Result<Boolean> {
        return apiService.signUp(id, pw, email).toResult().map { it.success }
    }
}
