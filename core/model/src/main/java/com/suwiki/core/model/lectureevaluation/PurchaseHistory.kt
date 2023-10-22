package com.suwiki.core.model.lectureevaluation

import java.time.LocalDateTime

data class PurchaseHistory(
  val id: Long,
  val lectureName: String,
  val professor: String,
  val majorType: String,
  val createDate: LocalDateTime,
)
