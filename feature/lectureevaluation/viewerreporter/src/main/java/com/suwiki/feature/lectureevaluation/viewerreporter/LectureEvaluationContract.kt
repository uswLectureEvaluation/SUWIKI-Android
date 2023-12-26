package com.suwiki.feature.lectureevaluation.viewerreporter

data class LectureEvaluationState(
  val showOnboardingBottomSheet: Boolean = false,
)

sealed interface LectureEvaluationSideEffect {
  data object NavigateToLogin : LectureEvaluationSideEffect
  data object NavigateToSignUp : LectureEvaluationSideEffect
}
