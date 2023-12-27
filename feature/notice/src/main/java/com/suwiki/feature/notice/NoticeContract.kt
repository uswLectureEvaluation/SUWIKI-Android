package com.suwiki.feature.notice

data class NoticeState(
  val isLoading: Boolean = false,
)

sealed interface NoticeSideEffect {
  data object NavigateNoticeDetail : NoticeSideEffect
}
