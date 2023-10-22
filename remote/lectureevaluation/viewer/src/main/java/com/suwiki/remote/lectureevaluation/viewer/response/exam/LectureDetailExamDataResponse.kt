package com.suwiki.remote.lectureevaluation.viewer.response.exam

import com.suwiki.core.model.lectureevaluation.LectureDetailExam
import com.suwiki.core.model.lectureevaluation.LectureDetailExamData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureDetailExamDataResponse(
  val data: List<LectureDetailExamResponse>,
  val examDataExist: Boolean,
  val written: Boolean,
)

internal fun LectureDetailExamDataResponse.toModel() = LectureDetailExamData(
  data = data.map { it.toModel() },
  examDataExist = examDataExist,
  written = written,
)

@Serializable
data class LectureDetailExamResponse(
  val id: Long,
  @SerialName("selectedSemester") val semester: String,
  val examInfo: String,
  val examType: String?,
  val examDifficulty: String,
  val content: String,
)

internal fun LectureDetailExamResponse.toModel() = LectureDetailExam(
  id = id,
  semester = semester,
  examInfo = examInfo,
  examType = examType,
  examDifficulty = examDifficulty,
  content = content,
)
