package com.suwiki.remote.request

data class ReportLectureRequest(
    val evaluateIdx: Long,
    val content: String = "",
)
