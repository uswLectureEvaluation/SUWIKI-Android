package com.suwiki.remote.lectureevaluation.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportExamRequest(
  val examIdx: Long,
  val content: String = "",
)
