package com.suwiki.core.model.lectureevaluation.exam

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class MyExamEvaluation(
  val id: Long? = null,
  val lectureName: String? = null, // 과목 이름
  val professor: String? = null, // 교수이름
  val majorType: String? = null, // 개설학과
  val selectedSemester: String? = null,
  val semesterList: String? = null,
  val examInfo: List<String> = listOf(""), // 시험 유형 ex) "족보, 교재, PPT, 필기, 응용, 실습, 과제",
  val examType: String? = null, // 시험 종류(바텀시트) ex) "중간고사", //기말고사, 쪽지, 기타
  val examDifficulty: String = "", // 시험 난이도
  val content: String = "",
) : java.io.Serializable
