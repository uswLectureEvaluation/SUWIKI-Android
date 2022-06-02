package com.kunize.uswtimetable.dataclass

import java.io.Serializable
import java.time.LocalDateTime

data class PurchaseHistory(
    val id: Long,
    val lectureName: String,
    val professor: String,
    val majorType: String,
    val createDate: LocalDateTime
) : Serializable