package com.suwiki.feature.myinfo

data class MyInfoState(
  val showMyInfoCard: Boolean = false,
  val isLoading: Boolean = false,
)

sealed interface MyInfoSideEffect {
  data object NavigateNotice : MyInfoSideEffect
  data object NavigateMyReview : MyInfoSideEffect
}
