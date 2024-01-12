package com.suwiki.remote.lectureevaluation.my.response

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import kotlinx.serialization.Serializable

@Serializable
data class MyExamEvaluationResponse(
  val id: Long? = null,
  val lectureName: String? = null, // 과목 이름
  val professor: String? = null, // 교수이름
  val majorType: String? = null, // 개설학과
  val selectedSemester: String? = null,
  val semesterList: String? = null,
  val examInfo: String, // 시험 방식
  val examType: String? = null,
  val examDifficulty: String, // 시험 난이도
  val content: String,
)

internal fun MyExamEvaluationResponse.toModel() = MyExamEvaluation(
  id = id,
  lectureName = lectureName,
  professor = professor,
  majorType = majorType,
  selectedSemester = selectedSemester,
  semesterList = semesterList,
  examInfo = examInfo.split(","),
  examType = examType,
  examDifficulty = examDifficulty,
  content = content,
)
