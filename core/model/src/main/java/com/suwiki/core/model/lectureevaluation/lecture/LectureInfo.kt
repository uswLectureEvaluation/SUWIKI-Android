package com.suwiki.core.model.lectureevaluation.lecture

data class LectureInfo(
  val semesterList: List<String>,
  val professor: String,
  val majorType: String,
  val lectureType: String?,
  val lectureName: String,
)
