package com.suwiki.domain.repository

import com.suwiki.domain.model.Result
import com.suwiki.domain.model.Suspension
import kotlinx.coroutines.flow.Flow

interface SuspensionRepository {
  suspend fun getSuspensionHistory(): Flow<Result<List<Suspension>>>
}
