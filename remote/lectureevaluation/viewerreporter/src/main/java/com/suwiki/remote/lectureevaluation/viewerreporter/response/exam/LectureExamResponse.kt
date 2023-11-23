package com.suwiki.remote.lectureevaluation.viewerreporter.response.exam

import com.suwiki.core.model.lectureevaluation.LectureExam
import kotlinx.serialization.Serializable

@Serializable
data class LectureExamResponse(
  val id: Long? = null,
  val lectureName: String? = null,
  val professor: String? = null,
  val majorType: String? = null,
  val selectedSemester: String? = null,
  val semesterList: String? = null,
  val examInfo: String,
  val examType: String? = null,
  val examDifficulty: String,
  val content: String,
)

internal fun LectureExamResponse.toModel() = LectureExam(
  id = id,
  lectureName = lectureName,
  professor = professor,
  majorType = majorType,
  selectedSemester = selectedSemester,
  semesterList = semesterList,
  examInfo = examInfo,
  examType = examType,
  examDifficulty = examDifficulty,
  content = content,
)
