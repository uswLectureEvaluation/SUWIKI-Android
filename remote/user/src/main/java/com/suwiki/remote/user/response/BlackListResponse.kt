package com.suwiki.remote.user.response

import com.suwiki.core.model.user.Suspension
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class BlackListResponse(
  val blackListReason: String, // 블랙리스트 사유
  val judgement: String, // 조치사항
  val createdAt: LocalDateTime, // 블랙리스트 된 시각
  val expiredAt: LocalDateTime, // 블랙리스트 풀리는 시각
)

internal fun BlackListResponse.toModel() = Suspension.Block(
  reason = blackListReason,
  judgement = judgement,
  createdAt = createdAt.toJavaLocalDateTime(),
  expiredAt = expiredAt.toJavaLocalDateTime(),
)
