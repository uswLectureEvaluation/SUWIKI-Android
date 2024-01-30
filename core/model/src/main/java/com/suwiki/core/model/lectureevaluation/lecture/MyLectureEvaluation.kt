package com.suwiki.core.model.lectureevaluation.lecture

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class MyLectureEvaluation(
  val id: Long = 0,
  val lectureInfo: LectureInfo = LectureInfo(),
  val selectedSemester: String = "",
  val totalAvg: Float = 2.5f, // 총점
  val satisfaction: Float = 2.5f, // 만족도
  val learning: Float = 2.5f, // 배움지수
  val honey: Float = 2.5f, // 꿀강지수
  val team: Int = 0,
  val difficulty: Int = 1,
  val homework: Int = 1,
  val content: String = "",
)
