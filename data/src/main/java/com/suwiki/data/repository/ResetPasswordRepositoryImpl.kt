package com.suwiki.data.repository

import com.suwiki.data.db.request.ResetPasswordRequest
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.toResult
import com.suwiki.domain.di.AuthApiService
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.ResetPasswordRepository
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(
    @AuthApiService private val apiService: ApiService,
) : ResetPasswordRepository {
    /**
     * @return 비밀번호 초기화 성공시 true, 실패시 false 반환
     * */
    override suspend fun resetPassword(current: String, new: String): Result<Boolean> =
        apiService.resetPassword(
            ResetPasswordRequest(
                currentPassword = current,
                newPassword = new,
            ),
        ).toResult().map { it.success }
}
