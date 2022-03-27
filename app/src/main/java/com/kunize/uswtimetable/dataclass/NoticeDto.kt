package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime

data class NoticeDto(
    val id: String,
    val title: String,
    @SerializedName("modifiedDate") val date: LocalDateTime
): Serializable

data class NoticeDetailData(
    val id: Long,
    val title: String,
    @SerializedName("modifiedDate") val date: LocalDateTime,
    val content: String
): Serializable

data class NoticeListDto(
    val data: List<NoticeDto>
): Serializable

data class NoticeDetailDto(
    val data: NoticeDetailData
): Serializable