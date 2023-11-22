package com.suwiki.remote.lectureevaluation.report.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportLectureRequest(
  val evaluateIdx: Long,
  val content: String = "",
)
