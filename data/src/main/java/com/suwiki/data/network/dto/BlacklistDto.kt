package com.suwiki.data.network.dto

import java.io.Serializable
import java.time.LocalDateTime

data class BlacklistDto(
  val blackListReason: String, // 블랙리스트 사유
  val judgement: String, // 조치 사항
  val createdAt: LocalDateTime, // 블랙리스트 된 시각
  val expiredAt: LocalDateTime, // 블랙리스트 풀리는 시각
) : Serializable
