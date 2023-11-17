package com.suwiki.remote.lectureevaluation.viewerreporter.request

data class ReportLectureRequest(
  val evaluateIdx: Long,
  val content: String = "",
)
