package com.suwiki.feature.myinfo.myreview

data class MyReviewState(
  val isLoading: Boolean = false,
  val currentPage: Int = 0,
)

sealed interface MyReviewSideEffect {
  data object PopBackStack : MyReviewSideEffect
  data object NavigateMyClassReview : MyReviewSideEffect
  data object NavigateMyTestReview : MyReviewSideEffect
}
