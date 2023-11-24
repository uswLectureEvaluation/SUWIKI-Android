package com.suwiki.core.model.lectureevaluation.lecture

data class MyLectureEvaluation(
  val id: Long,
  val lectureInfo: LectureInfo,
  val selectedSemester: String,
  val totalAvg: Float, // 총점
  val satisfaction: Float, // 만족도
  val learning: Float, // 배움지수
  val honey: Float, // 꿀강지수
  val team: Int, // 조별모임 유무(없음 == 0, 있음 == 1)
  val difficulty: Int, // 학점 잘주는가? (까다로움 == 0, 보통 == 1, 학점느님 ==2)
  val homework: Int, // 과제양 (없음 ==0, 보통 == 1, 많음 == 2)
  val content: String,
)
