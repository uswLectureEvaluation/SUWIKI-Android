package com.suwiki.feature.lectureevaluation.viewerreporter

import com.suwiki.feature.lectureevaluation.viewerreporter.model.LectureEvaluation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationState(
  val filteredLectureEvaluationList: PersistentList<LectureEvaluation> = persistentListOf(),
  val filterDataList: PersistentList<String> = persistentListOf(
    "최근 올라온 강의",
    "꿀 강의",
    "만족도 높은 강의",
    "배울게 많은 강의",
    "BEST 강의",
  ),
  val showOnboardingBottomSheet: Boolean = false,
  val showFilterSelectionBottomSheet: Boolean = false,
  val selectedOpenMajor: String = "",
  val selectedFilter: String = "최근 올라온 강의",
  val currentPage: Int = 0,
  val searchValue: String = "",
  val isLoading: Boolean = false,
)

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
}
