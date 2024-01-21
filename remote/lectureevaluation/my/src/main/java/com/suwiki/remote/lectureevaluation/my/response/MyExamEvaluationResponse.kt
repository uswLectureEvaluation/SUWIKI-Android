package com.suwiki.remote.lectureevaluation.my.response

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import kotlinx.serialization.Serializable

@Serializable
data class MyExamEvaluationResponse(
  val id: Long = -1L,
  val lectureName: String = "", // 과목 이름
  val professor: String = "", // 교수이름
  val majorType: String = "", // 개설학과
  val selectedSemester: String = "",
  val semesterList: String = "",
  val examInfo: String, // 시험 방식
  val examType: String = "",
  val examDifficulty: String, // 시험 난이도
  val content: String,
)

internal fun MyExamEvaluationResponse.toModel() = MyExamEvaluation(
  id = id,
  lectureName = lectureName,
  professor = professor,
  majorType = majorType,
  selectedSemester = selectedSemester,
  semesterList = semesterList.replace(" ","").split(","),
  examInfo = examInfo.replace(" ","").split(","),
  examType = examType,
  examDifficulty = examDifficulty,
  content = content,
)
