package com.suwiki.remote.response.notice

import com.suwiki.model.NoticeDetail
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class NoticeDetailResponse(
    val id: Long,
    val title: String,
    @SerialName("modifiedDate") val date: LocalDateTime?,
    val content: String,
)

internal fun NoticeDetailResponse.toModel() = NoticeDetail(
    id = id,
    title = title,
    date = date?.toJavaLocalDateTime(),
    content = content,
)
