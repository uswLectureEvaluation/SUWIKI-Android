package com.suwiki.feature.lectureevaluation.viewerreporter

import com.suwiki.feature.lectureevaluation.viewerreporter.model.LectureEvaluation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationState(
  val filteredAllOpenMajorList: PersistentList<LectureEvaluation> = persistentListOf(),
  val showOnboardingBottomSheet: Boolean = false,
  val selectedOpenMajor: String = "",
)

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
}
