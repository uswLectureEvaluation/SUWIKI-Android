package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteRestrictionHistoryDataSource
import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.remote.api.RestrictionHistoryApi
import com.suwiki.remote.response.user.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteRestrictionHistoryDataSourceImpl @Inject constructor(
    private val restrictionHistoryApi: RestrictionHistoryApi,
) : RemoteRestrictionHistoryDataSource {

    override suspend fun getSuspensionHistory(): Result<List<Suspension.Ban>> {
        return restrictionHistoryApi.getSuspensionHistory().toResult()
            .map { result -> result.map { it.toModel() } }
    }

    override suspend fun getBlacklistHistory(): Result<List<Suspension.Block>> {
        return restrictionHistoryApi.getBlacklistHistory().toResult()
            .map { result -> result.map { it.toModel() } }
    }
}
