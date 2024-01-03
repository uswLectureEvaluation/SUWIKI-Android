package com.suwiki.feature.myinfo.myreview.testreview

data class MyTestReviewEditState(
  val isLoading: Boolean = false,
  val selectedSemester: String = "",
  val selectedTestType: String = "",
  val testReview: String = "",
  val examDifficulty: String = "",
  val examInfo: String = "",
  val examType: String? = null,
  val difficultyEasyChecked: Boolean = false,
  val difficultyNormalChecked: Boolean = false,
  val difficultyHardChecked: Boolean = false,
  val testTypeExamGuidesChecked: Boolean = false,
  val testTypeBookChecked: Boolean = false,
  val testTypeNotesChecked: Boolean = false,
  val testTypePPTChecked: Boolean = false,
  val testTypeApplyChecked: Boolean = false,
  val testTypePracticeChecked: Boolean = false,
  val testTypeHomeworkChecked: Boolean = false,
  val showSemesterBottomSheet: Boolean = false,
  val showTestTypeBottomSheet: Boolean = false,
  val showDeleteTestReviewDialog: Boolean = false,
)

sealed interface MyTestReviewEditSideEffect {
  data object PopBackStack : MyTestReviewEditSideEffect
}
