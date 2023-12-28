package com.suwiki.feature.notice

data class NoticeDetailState(
  val isLoading: Boolean = false,
)

sealed interface NoticeDetailSideEffect {
  data object PopBackStack : NoticeDetailSideEffect
}
