package com.suwiki.data.repository

import com.suwiki.data.datasource.SuspensionHistoryDataSource
import com.suwiki.data.network.dto.converter.toDomain
import com.suwiki.domain.model.Result
import com.suwiki.domain.model.Suspension
import com.suwiki.domain.repository.SuspensionRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class SuspensionRepositoryImpl @Inject constructor(
    private val dataSource: SuspensionHistoryDataSource,
) : SuspensionRepository {

    override suspend fun getSuspensionHistory(): Flow<Result<List<Suspension>>> = flow {
        while (currentCoroutineContext().isActive) {
            val banHistoryResult = dataSource.getBanHistory()
            val blacklistHistoryResult = dataSource.getBlacklistHistory()
            val result = mutableListOf<Suspension>()

            when (banHistoryResult) {
                is Result.Failure -> emit(banHistoryResult)
                is Result.Success -> result.addAll(banHistoryResult.data.map { it.toDomain() })
            }
            when (blacklistHistoryResult) {
                is Result.Failure -> emit(blacklistHistoryResult)
                is Result.Success -> result.addAll(blacklistHistoryResult.data.map { it.toDomain() })
            }
            result.sortWith { p0, p1 ->
                if (p0 is Suspension.Ban && p1 is Suspension.Ban) {
                    p0.createdAt.compareTo(p1.createdAt)
                } else if (p0 is Suspension.Block && p1 is Suspension.Block) {
                    p0.createdAt.compareTo(p1.createdAt)
                } else if (p0 is Suspension.Ban && p1 is Suspension.Block) {
                    p0.createdAt.compareTo(p1.createdAt)
                } else if (p0 is Suspension.Block && p1 is Suspension.Ban) {
                    p0.createdAt.compareTo(p1.createdAt)
                } else {
                    throw IllegalArgumentException("Invalid suspension type")
                }
            }
            emit(Result.Success(result))
            delay(500)
        }
    }
}
