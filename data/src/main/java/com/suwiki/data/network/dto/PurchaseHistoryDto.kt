package com.suwiki.data.network.dto

import java.io.Serializable
import java.time.LocalDateTime

data class PurchaseHistoryDto(
  val id: Long,
  val lectureName: String,
  val professor: String,
  val majorType: String,
  val createDate: LocalDateTime,
) : Serializable
