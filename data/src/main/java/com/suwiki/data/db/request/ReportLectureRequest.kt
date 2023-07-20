package com.suwiki.data.db.request

data class ReportLectureRequest(
    val evaluateIdx: Long,
    val content: String = "",
)
