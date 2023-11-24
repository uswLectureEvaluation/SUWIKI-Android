package com.suwiki.core.model.lectureevaluation.lecture

data class LectureEvaluationExtraAverage(
  val id: Long,
  val lectureInfo: LectureInfo,
  val lectureTotalAvg: Float,
  val lectureSatisfactionAvg: Float,
  val lectureHoneyAvg: Float,
  val lectureLearningAvg: Float,
  val lectureTeamAvg: Float,
  val lectureDifficultyAvg: Float,
  val lectureHomeworkAvg: Float,
)
