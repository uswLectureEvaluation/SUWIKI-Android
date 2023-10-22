package com.suwiki.data.db.request

data class ReportExamRequest(
  val evaluateIdx: Long,
  val content: String = "",
)
