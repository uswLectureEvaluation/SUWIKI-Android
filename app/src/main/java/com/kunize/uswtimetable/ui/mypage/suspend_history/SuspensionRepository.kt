package com.kunize.uswtimetable.ui.mypage.suspend_history

import com.kunize.uswtimetable.data.local.Suspension
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class SuspensionRepository(private val dataSource: SuspensionHistoryRemoteDataSource) {
    private val items = mutableListOf<Suspension>()
    private val _suspensionHistory = MutableStateFlow<List<Suspension>>(emptyList())
    val suspensionHistory = _suspensionHistory.asStateFlow()

    suspend fun getSuspensionHistory() {
        withContext(Dispatchers.IO) {
            val banHistoryResponse = dataSource.getBanHistory()
            val blacklistHistoryResponse = dataSource.getBlacklistHistory()
            banHistoryResponse.onSuccess {
                val banHistory = data.map { item ->
                    Suspension.Ban(
                        item.bannedReason,
                        item.judgement,
                        item.createdAt,
                        item.expiredAt
                    )
                }
                items.addAll(banHistory)
            }
            blacklistHistoryResponse.onSuccess {
                val blockHistory = data.map { item ->
                    Suspension.Block(
                        item.blackListReason,
                        item.judgement,
                        item.createdAt,
                        item.expiredAt
                    )
                }
                items.addAll(blockHistory)
            }
            items.sortWith { p0, p1 ->
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
            _suspensionHistory.emit(items)
        }
    }
}
