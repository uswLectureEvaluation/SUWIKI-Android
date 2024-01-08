package com.suwiki.feature.myinfo.myreview.testreview

data class MyTestReviewEditState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val selectedSemester: String = "",
  val selectedTestType: String = "",
  val testReview: String = "",
  val examDifficulty: String = "",
  val examInfo: String = "",
  val examType: String? = null,
  val showSemesterBottomSheet: Boolean = false,
  val showTestTypeBottomSheet: Boolean = false,
  val showDeleteTestReviewDialog: Boolean = false,
  val showDeleteTestReviewToastMessage: String = "",
  val showDeleteTestReviewToastVisible: Boolean = false,
)

sealed interface MyTestReviewEditSideEffect {
  data object PopBackStack : MyTestReviewEditSideEffect
}
