package com.suwiki.common.model.notice

import androidx.compose.runtime.Stable
import java.time.LocalDateTime

@Stable
data class NoticeDetail(
  val id: Long = 0,
  val title: String = "",
  val date: LocalDateTime? = null,
  val content: String = "",
)
