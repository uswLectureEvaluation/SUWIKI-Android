package com.suwiki.presentation.notice

import com.suwiki.common.model.notice.Notice
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class NoticeState(
  val isLoading: Boolean = false,
  val noticeList: PersistentList<Notice> = persistentListOf(),
)

sealed interface NoticeSideEffect {
  data class NavigateNoticeDetail(val noticeId: Long) : NoticeSideEffect
  data object PopBackStack : NoticeSideEffect
  data class HandleException(val exception: Throwable) : NoticeSideEffect
}
