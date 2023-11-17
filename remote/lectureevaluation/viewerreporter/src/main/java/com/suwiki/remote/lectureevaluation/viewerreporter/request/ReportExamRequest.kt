package com.suwiki.remote.lectureevaluation.viewerreporter.request

data class ReportExamRequest(
  val evaluateIdx: Long,
  val content: String = "",
)
