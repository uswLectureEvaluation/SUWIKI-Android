package com.suwiki.feature.lectureevaluation.viewerreporter

import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationState(
  val lectureEvaluationList: PersistentList<LectureEvaluationAverage?> = persistentListOf(),
  val showOnboardingBottomSheet: Boolean = false,
  val showAgreementBottomSheet: Boolean = false,
  val isCheckedTerm: Boolean = false,
  val isCheckedPersonalPolicy: Boolean = false,
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
      lectureEvaluationList.isEmpty() &&
      isLoading.not()

  val isEnabledAgreementButton: Boolean = isCheckedTerm && isCheckedPersonalPolicy
}

sealed interface LectureEvaluationSideEffect {
  data object NavigateLogin : LectureEvaluationSideEffect
  data object NavigateSignUp : LectureEvaluationSideEffect
  data object OpenTermWebSite : LectureEvaluationSideEffect
  data object OpenPersonalPolicyWebSite : LectureEvaluationSideEffect
  data object ScrollToTop : LectureEvaluationSideEffect
  data class HandleException(val throwable: Throwable) : LectureEvaluationSideEffect
}
