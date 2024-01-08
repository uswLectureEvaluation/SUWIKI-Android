package com.suwiki.feature.lectureevaluation.viewerreporter

import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationState(
  val lectureEvaluationList: PersistentList<LectureEvaluationAverage?> = persistentListOf(),
  val showOnboardingBottomSheet: Boolean = false,
  val showAlignBottomSheet: Boolean = false,
  val selectedOpenMajor: String = "전체",
  val selectedAlignPosition: Int = 0,
  val searchValue: String = "",
  val isLoading: Boolean = false,
) {
  val alignValue = LectureAlign.entries[selectedAlignPosition]

  val showSearchEmptyResultScreen: Boolean =
    searchValue.isNotEmpty() &&
      selectedOpenMajor.isNotEmpty() &&
      lectureEvaluationList.isEmpty()
}

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
}
