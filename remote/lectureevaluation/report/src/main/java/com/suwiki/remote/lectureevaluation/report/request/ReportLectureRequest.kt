package com.suwiki.remote.lectureevaluation.report.request

data class ReportLectureRequest(
    val evaluateIdx: Long,
    val content: String = "",
)
