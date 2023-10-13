package com.suwiki.lectureevaluation.report.request

data class ReportLectureRequest(
    val evaluateIdx: Long,
    val content: String = "",
)
