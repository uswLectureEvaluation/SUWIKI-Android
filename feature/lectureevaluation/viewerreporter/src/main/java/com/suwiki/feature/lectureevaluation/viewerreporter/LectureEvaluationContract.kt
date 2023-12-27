package com.suwiki.feature.lectureevaluation.viewerreporter

data class LectureEvaluationState(
  val showOnboardingBottomSheet: Boolean = false,
)

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
}
