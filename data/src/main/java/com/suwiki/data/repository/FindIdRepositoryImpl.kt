package com.suwiki.data.repository

import com.suwiki.data.db.request.FindIdRequest
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.toDomainResult
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.FindIdRepository
import javax.inject.Inject

class FindIdRepositoryImpl @Inject constructor(
    @OtherApiService private val apiService: ApiService,
) : FindIdRepository {
    override suspend fun findId(email: String): Result<Boolean> =
        apiService.findId(FindIdRequest(email)).toDomainResult().map { it.success }
}
