package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class NoticeDto(
    val id: String,
    val title: String,
    @SerializedName("modifiedDate") val date: LocalDateTime
)
