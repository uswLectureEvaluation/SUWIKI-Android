package com.suwiki.core.model.lectureevaluation.lecture

data class LectureEvaluationAverage(
  val id: Long,
  val lectureInfo: LectureInfo,
  val lectureTotalAvg: Float,
  val lectureSatisfactionAvg: Float,
  val lectureHoneyAvg: Float,
  val lectureLearningAvg: Float,
)
