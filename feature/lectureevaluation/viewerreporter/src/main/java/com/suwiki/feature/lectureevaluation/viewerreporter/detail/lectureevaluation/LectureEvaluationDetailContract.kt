package com.suwiki.feature.lectureevaluation.viewerreporter.detail.lectureevaluation

data class LectureEvaluationDetailState(
  val isLoading: Boolean = false,
  val currentTabPage: Int = 0,
)
sealed interface LectureEvaluationDetailSideEffect {
  data object PopBackStack : LectureEvaluationDetailSideEffect
  data class HandleException(val throwable: Throwable) : LectureEvaluationDetailSideEffect
}
