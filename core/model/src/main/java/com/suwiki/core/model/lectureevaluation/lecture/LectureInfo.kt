package com.suwiki.core.model.lectureevaluation.lecture

import androidx.compose.runtime.Stable

@Stable
data class LectureInfo(
  val semesterList: List<String> = listOf(""),
  val professor: String = "",
  val majorType: String = "",
  val lectureType: String? = null,
  val lectureName: String = "",
)
