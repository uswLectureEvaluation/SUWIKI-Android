package com.suwiki.common.model.lectureevaluation.lecture

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class LectureInfo(
  val semesterList: List<String> = listOf(""),
  val professor: String = "",
  val majorType: String = "",
  val lectureType: String = "",
  val lectureName: String = "",
)
