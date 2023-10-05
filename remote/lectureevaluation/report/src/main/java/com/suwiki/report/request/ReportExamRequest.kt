package com.suwiki.report.request

data class ReportExamRequest(
    val evaluateIdx: Long,
    val content: String = "",
)