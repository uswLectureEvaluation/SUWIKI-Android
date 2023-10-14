package com.suwiki.notice.response

import com.suwiki.model.Notice
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticeResponse(
    val id: Long,
    val title: String,
    @SerialName("modifiedDate") val date: LocalDateTime,
)

internal fun NoticeResponse.toModel() = Notice(
    id = id,
    title = title,
    date = date.toJavaLocalDateTime()
)