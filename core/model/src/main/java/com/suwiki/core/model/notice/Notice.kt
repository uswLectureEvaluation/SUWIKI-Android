package com.suwiki.core.model.notice

import java.time.LocalDateTime

data class Notice(
    val id: Long,
    val title: String,
    val date: LocalDateTime?,
)
