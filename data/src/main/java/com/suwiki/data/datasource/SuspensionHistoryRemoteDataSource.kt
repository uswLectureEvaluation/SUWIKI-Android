package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.BlacklistDto
import com.suwiki.data.network.dto.SuspensionHistoryDto
import com.suwiki.data.network.toResult
import com.suwiki.domain.model.Result
import javax.inject.Inject

class SuspensionHistoryRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) : SuspensionHistoryDataSource {
    override suspend fun getBanHistory(): Result<List<SuspensionHistoryDto>> =
        apiService.getSuspensionHistory().toResult()

    override suspend fun getBlacklistHistory(): Result<List<BlacklistDto>> =
        apiService.getBlacklistHistory().toResult()
}
