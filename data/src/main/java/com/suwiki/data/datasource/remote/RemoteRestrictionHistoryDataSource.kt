package com.suwiki.data.datasource.remote

import com.suwiki.model.Result
import com.suwiki.model.Suspension

interface RemoteRestrictionHistoryDataSource {

    suspend fun getSuspensionHistory(): Result<List<Suspension.Ban>>

    suspend fun getBlacklistHistory(): Result<List<Suspension.Block>>
}
