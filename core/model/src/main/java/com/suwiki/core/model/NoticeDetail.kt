package com.suwiki.core.model

import java.time.LocalDateTime

data class NoticeDetail(
    val id: Long,
    val title: String,
    val date: LocalDateTime?,
    val content: String,
)
