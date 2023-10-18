package com.suwiki.core.model.user

import java.time.LocalDateTime

sealed class Suspension {
    data class Ban(
        val reason: String,
        val judgement: String,
        val createdAt: LocalDateTime,
        val expiredAt: LocalDateTime,
    ) : Suspension()

    data class Block(
        val reason: String,
        val judgement: String,
        val createdAt: LocalDateTime,
        val expiredAt: LocalDateTime,
    ) : Suspension()
}
