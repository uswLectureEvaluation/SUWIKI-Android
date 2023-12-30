package com.suwiki.feature.notice

import androidx.compose.runtime.Stable
import com.suwiki.core.model.notice.NoticeDetail

data class NoticeDetailState(
  val isLoading: Boolean = false,
  @Stable
  val noticeDetail: NoticeDetail = NoticeDetail(),
)

sealed interface NoticeDetailSideEffect {
  data object PopBackStack : NoticeDetailSideEffect
}
