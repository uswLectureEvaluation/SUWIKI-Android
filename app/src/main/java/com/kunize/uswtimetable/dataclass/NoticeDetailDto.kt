package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoticeDetailDto(
    @SerializedName("notice_id") val id: String,
    @SerializedName("notice_title") val title: String,
    @SerializedName("notice_date") val date: String,
    @SerializedName("notice_detail") val contents: String
): Serializable
