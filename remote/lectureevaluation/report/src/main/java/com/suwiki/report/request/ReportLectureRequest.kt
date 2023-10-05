package com.suwiki.report.request

data class ReportLectureRequest(
    val evaluateIdx: Long,
    val content: String = "",
)
