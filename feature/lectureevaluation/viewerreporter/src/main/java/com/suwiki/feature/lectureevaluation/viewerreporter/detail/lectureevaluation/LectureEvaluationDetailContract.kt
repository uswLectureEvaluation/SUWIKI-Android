package com.suwiki.feature.lectureevaluation.viewerreporter.detail.lectureevaluation

import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo

data class LectureEvaluationDetailState(
  val isLoading: Boolean = false,
  val currentTabPage: Int = 0,
  val lectureInfo: LectureInfo = LectureInfo(),
  val lectureTotalAvg: Float = 0f,
  val lectureSatisfactionAvg: Float = 0f,
  val lectureHoneyAvg: Float = 0f,
  val lectureLearningAvg: Float = 0f,
  val lectureTeamAvg: Float = 0f,
  val lectureDifficultyAvg: Float = 0f,
  val lectureHomeworkAvg: Float = 0f,
)
sealed interface LectureEvaluationDetailSideEffect {
  data object PopBackStack : LectureEvaluationDetailSideEffect
  data class HandleException(val throwable: Throwable) : LectureEvaluationDetailSideEffect
}
