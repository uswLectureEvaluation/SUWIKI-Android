package com.suwiki.remote.request

data class ReportExamRequest(
    val evaluateIdx: Long,
    val content: String = "",
)