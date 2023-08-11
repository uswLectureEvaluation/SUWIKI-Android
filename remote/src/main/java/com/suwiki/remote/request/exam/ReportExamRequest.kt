package com.suwiki.remote.request.exam

data class ReportExamRequest(
    val evaluateIdx: Long,
    val content: String = "",
)