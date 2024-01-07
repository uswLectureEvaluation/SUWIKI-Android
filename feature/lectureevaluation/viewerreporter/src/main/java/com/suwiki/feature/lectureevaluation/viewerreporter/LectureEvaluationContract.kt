package com.suwiki.feature.lectureevaluation.viewerreporter

data class LectureEvaluationState(
  val showOnboardingBottomSheet: Boolean = false,
  val showAgreementBottomSheet: Boolean = false,
  val isCheckedTerm: Boolean = false,
  val isCheckedPersonalPolicy: Boolean = false,
  val selectedOpenMajor: String = "",
) {
  val isEnabledAgreementButton: Boolean = isCheckedTerm && isCheckedPersonalPolicy
}

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
  data object OpenTermWebSite : LectureEvaluationSideEffect
  data object OpenPersonalPolicyWebSite : LectureEvaluationSideEffect
}
