package com.suwiki.feature.notice

import com.suwiki.core.model.notice.NoticeDetail

data class NoticeDetailState(
  val isLoading: Boolean = false,
  val noticeDetail: NoticeDetail = NoticeDetail(),
)

sealed interface NoticeDetailSideEffect {
  data object PopBackStack : NoticeDetailSideEffect
}
