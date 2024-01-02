package com.suwiki.feature.lectureevaluation.viewerreporter

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationState(
  val lectureEvaluationList: PersistentList<LectureEvaluationAverage?> = persistentListOf(),
  val showOnboardingBottomSheet: Boolean = false,
  val showAlignBottomSheet: Boolean = false,
  val selectedOpenMajor: String = "",
  val selectedFilter: String = "",
  val currentPage: Int = 0,
  val searchValue: String = "",
  val isLoading: Boolean = false,
)

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
}
