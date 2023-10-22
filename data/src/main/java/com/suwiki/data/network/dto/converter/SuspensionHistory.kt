package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.BlacklistDto
import com.suwiki.data.network.dto.SuspensionHistoryDto
import com.suwiki.domain.model.Suspension

fun SuspensionHistoryDto.toDomain(): Suspension {
  return Suspension.Ban(
    bannedReason,
    judgement,
    createdAt,
    expiredAt,
  )
}

fun BlacklistDto.toDomain(): Suspension {
  return Suspension.Block(
    blackListReason,
    judgement,
    createdAt,
    expiredAt,
  )
}
