package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoticeDetailDto(
    val id: Long,
    val title: String,
    @SerializedName("modifiedDate") val date: String,
    val content: String
): Serializable
