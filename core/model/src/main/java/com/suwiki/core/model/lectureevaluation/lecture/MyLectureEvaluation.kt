package com.suwiki.core.model.lectureevaluation.lecture

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class MyLectureEvaluation(
  val id: Long = 0,
  val lectureInfo: LectureInfo = LectureInfo(),
  val selectedSemester: String = "",
  val totalAvg: Float = 0f, // 총점
  val satisfaction: Float = 0f, // 만족도
  val learning: Float = 0f, // 배움지수
  val honey: Float = 0f, // 꿀강지수
  val team: Int = 0, // 조별모임 유무(없음 == 0, 있음 == 1)
  val difficulty: Int = 0, // 학점 잘주는가? (까다로움 == 0, 보통 == 1, 학점느님 ==2)
  val homework: Int = 0, // 과제양 (없음 ==0, 보통 == 1, 많음 == 2)
  val content: String = "",
)
