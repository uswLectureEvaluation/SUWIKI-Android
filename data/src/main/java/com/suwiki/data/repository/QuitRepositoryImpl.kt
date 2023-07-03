package com.suwiki.data.repository

import com.suwiki.data.db.request.QuitRequest
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.toResult
import com.suwiki.domain.di.AuthApiService
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.QuitRepository
import javax.inject.Inject

class QuitRepositoryImpl @Inject constructor(
    @AuthApiService private val apiService: ApiService,
) : QuitRepository {

    /**
     * @return 회원 탈퇴 성공시 true, 실패시 false 반환
     * */
    override suspend fun quit(id: String, password: String): Result<Boolean> =
        apiService.quit(
            QuitRequest(id = id, password = password),
        ).toResult().map { it.success }
}
