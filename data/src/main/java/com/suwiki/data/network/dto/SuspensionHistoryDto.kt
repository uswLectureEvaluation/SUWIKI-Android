package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SuspensionHistoryDto(
  @SerializedName("restrictedReason") val bannedReason: String, // 정지 사유
  val judgement: String, // 조치 사항
  val createdAt: LocalDateTime, // 정지 조치 시각
  @SerializedName("restrictingDate") val expiredAt: LocalDateTime, // 정지 풀리는 시각
)
