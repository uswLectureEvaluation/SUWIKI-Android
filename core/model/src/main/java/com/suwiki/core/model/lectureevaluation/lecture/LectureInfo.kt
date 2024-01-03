package com.suwiki.core.model.lectureevaluation.lecture

data class LectureInfo(
  val semesterList: List<String> = listOf(""),
  val professor: String = "",
  val majorType: String = "",
  val lectureType: String? = null,
  val lectureName: String = "",
)
