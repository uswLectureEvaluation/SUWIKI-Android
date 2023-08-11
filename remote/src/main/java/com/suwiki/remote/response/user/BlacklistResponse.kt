package com.suwiki.remote.response.user

import com.suwiki.model.Suspension
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class BlacklistResponse(
    val blackListReason: String, // 블랙리스트 사유
    val judgement: String, // 조치 사항
    val createdAt: LocalDateTime, // 블랙리스트 된 시각
    val expiredAt: LocalDateTime, // 블랙리스트 풀리는 시각
)

internal fun BlacklistResponse.toModel() = Suspension.Block(
    reason = blackListReason,
    judgement = judgement,
    createdAt = createdAt.toJavaLocalDateTime(),
    expiredAt = expiredAt.toJavaLocalDateTime(),
)
