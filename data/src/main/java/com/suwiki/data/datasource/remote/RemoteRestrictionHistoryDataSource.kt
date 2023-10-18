package com.suwiki.data.datasource.remote

import com.suwiki.core.model.user.Suspension

interface RemoteRestrictionHistoryDataSource {

    suspend fun getSuspensionHistory(): List<Suspension.Ban>

    suspend fun getBlacklistHistory(): List<Suspension.Block>
}
