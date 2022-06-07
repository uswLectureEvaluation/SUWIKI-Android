package com.kunize.uswtimetable.dataclass

import java.time.LocalDateTime

data class SuspensionHistory(
    val bannedReason: String, // 사유
    val judgement: String, // 조치 사항
    val createdAt: LocalDateTime, // 발동 시각
    val expiredAt: LocalDateTime // 해제 시각
)