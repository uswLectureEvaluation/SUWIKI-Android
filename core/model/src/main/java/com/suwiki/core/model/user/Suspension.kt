package com.suwiki.core.model.user

import kotlinx.serialization.SerialName
import java.time.LocalDateTime

sealed class Suspension {
  data class Ban(
    @SerialName("restrictedReason")
    val reason: String,
    val judgement: String,
    val createdAt: LocalDateTime,
    @SerialName("restrictingDate")
    val expiredAt: LocalDateTime,
  ) : Suspension()

  data class Block(
    val reason: String,
    val judgement: String,
    val createdAt: LocalDateTime,
    val expiredAt: LocalDateTime,
  ) : Suspension()
}
