package com.suwiki.core.model.notice

import java.time.LocalDateTime

data class NoticeDetail(
  val id: Long = 0,
  val title: String = "",
  val date: LocalDateTime? = null,
  val content: String = "",
)
