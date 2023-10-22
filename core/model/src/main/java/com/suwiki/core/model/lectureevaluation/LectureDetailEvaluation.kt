package com.suwiki.core.model.lectureevaluation

data class LectureDetailEvaluation(
  val id: Long,
  val semester: String,
  val totalAvg: Float,
  val satisfaction: Float,
  val learning: Float,
  val honey: Float,
  val team: Int,
  val difficulty: Int,
  val homework: Int,
  val content: String,
)
