package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result
import com.suwiki.core.model.Suspension

interface RemoteRestrictionHistoryDataSource {

    suspend fun getSuspensionHistory(): Result<List<Suspension.Ban>>

    suspend fun getBlacklistHistory(): Result<List<Suspension.Block>>
}
