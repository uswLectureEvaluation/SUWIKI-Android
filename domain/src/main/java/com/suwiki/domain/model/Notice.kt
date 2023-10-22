package com.suwiki.domain.model

import java.time.LocalDateTime

data class Notice(
  val id: Long,
  val title: String,
  val date: LocalDateTime?,
  val content: String,
)
