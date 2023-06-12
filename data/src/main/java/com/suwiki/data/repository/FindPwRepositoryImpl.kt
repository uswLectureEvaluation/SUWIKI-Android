package com.suwiki.data.repository

import com.kunize.uswtimetable.domain.di.OtherApiService
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.FindPwRepository
import javax.inject.Inject

// TODO FindIdRepository 와 통합
class FindPwRepositoryImpl @Inject constructor(
    @OtherApiService private val apiService: ApiService,
) : FindPwRepository {

    override suspend fun findPw(id: String, email: String): Result<Boolean> =
        apiService.findPassword(id, email).toResult().map { it.success }
}
