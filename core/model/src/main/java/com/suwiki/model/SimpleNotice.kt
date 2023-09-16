package com.suwiki.model

import java.time.LocalDateTime

data class SimpleNotice(
    val id: Long,
    val title: String,
    val date: LocalDateTime,
)
