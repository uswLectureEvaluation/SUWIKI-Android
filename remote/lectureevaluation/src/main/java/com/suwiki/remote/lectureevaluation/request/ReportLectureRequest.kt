package com.suwiki.remote.lectureevaluation.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportLectureRequest(
  val evaluateIdx: Long,
  val content: String = "",
)
