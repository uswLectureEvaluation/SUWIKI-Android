package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Suspension

interface RemoteRestrictionHistoryDataSource {

    suspend fun getSuspensionHistory(): List<Suspension.Ban>

    suspend fun getBlacklistHistory(): List<Suspension.Block>
}
