package com.suwiki.lectureevaluation.report.request

data class ReportExamRequest(
    val evaluateIdx: Long,
    val content: String = "",
)