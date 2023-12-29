package com.suwiki.feature.notice

import com.suwiki.core.model.notice.Notice

data class NoticeState(
  val isLoading: Boolean = false,
  val noticeList: List<Notice> = listOf(),

)

sealed interface NoticeSideEffect {
  data object NavigateNoticeDetail : NoticeSideEffect
  data object PopBackStack : NoticeSideEffect
}
