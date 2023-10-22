package com.suwiki.data.datasource

import com.suwiki.data.network.dto.BlacklistDto
import com.suwiki.data.network.dto.SuspensionHistoryDto
import com.suwiki.domain.model.Result

interface SuspensionHistoryDataSource {
  suspend fun getBanHistory(): Result<List<SuspensionHistoryDto>>
  suspend fun getBlacklistHistory(): Result<List<BlacklistDto>>
}
