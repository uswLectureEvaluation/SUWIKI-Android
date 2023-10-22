package com.suwiki.remote.lectureevaluation.report.request

data class ReportExamRequest(
  val evaluateIdx: Long,
  val content: String = "",
)
