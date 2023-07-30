package com.suwiki.remote.request.evaluation

data class ReportLectureRequest(
    val evaluateIdx: Long,
    val content: String = "",
)
