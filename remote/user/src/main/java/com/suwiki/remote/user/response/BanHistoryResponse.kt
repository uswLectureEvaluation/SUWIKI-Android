package com.suwiki.remote.user.response

import com.suwiki.core.model.user.Suspension
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class BanHistoryResponse(
  val restrictedReason: String,
  val judgement: String,
  val createdAt: LocalDateTime,
  val restrictingDate: LocalDateTime,
)

internal fun BanHistoryResponse.toModel() = Suspension.Ban(
  reason = restrictedReason,
  judgement = judgement,
  createdAt = createdAt.toJavaLocalDateTime(),
  expiredAt = restrictingDate.toJavaLocalDateTime(),
)
