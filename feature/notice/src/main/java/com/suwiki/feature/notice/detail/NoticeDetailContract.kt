package com.suwiki.feature.notice.detail

import com.suwiki.core.model.notice.NoticeDetail
import com.suwiki.feature.notice.NoticeSideEffect

data class NoticeDetailState(
  val isLoading: Boolean = false,
  val noticeDetail: NoticeDetail = NoticeDetail(),
)

sealed interface NoticeDetailSideEffect {
  data object PopBackStack : NoticeDetailSideEffect
  data class HandleException(val exception: Throwable) : NoticeDetailSideEffect
}
