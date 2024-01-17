package com.suwiki.feature.myinfo.banhistory

import com.suwiki.core.model.user.Suspension
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class BanHistoryState(
  val isLoading: Boolean = false,
  val banHistory: PersistentList<Suspension.Ban> = persistentListOf(),
  val blackList: PersistentList<Suspension.Block> = persistentListOf(),
)

sealed interface BanHistorySideEffect {
  data object PopBackStack : BanHistorySideEffect
  data class HandleException(val throwable: Throwable) : BanHistorySideEffect
}
