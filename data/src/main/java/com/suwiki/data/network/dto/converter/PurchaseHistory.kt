package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.PurchaseHistoryDto
import com.suwiki.domain.model.PurchaseHistory

fun PurchaseHistoryDto.toDomain(): PurchaseHistory {
  return PurchaseHistory(
    id,
    lectureName,
    professor,
    majorType,
    createDate,
  )
}
