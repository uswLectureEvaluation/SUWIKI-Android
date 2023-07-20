package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime

data class NoticeDto(
    val id: Long,
    val title: String,
    @SerializedName("modifiedDate") val date: LocalDateTime,
) : Serializable

data class NoticeDetailDto(
    val id: Long,
    val title: String,
    @SerializedName("modifiedDate") val date: LocalDateTime?,
    val content: String,
) : Serializable
