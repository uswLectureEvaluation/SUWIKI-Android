package com.suwiki.feature.myinfo

data class MyInfoState(
  val showMyInfoCard: Boolean = false,
  val isLoading: Boolean = false,
  val loginId: String = "",
  val point: Int = 0,
)

sealed interface MyInfoSideEffect {
  data object NavigateNotice : MyInfoSideEffect
  data class NavigateMyEvaluation(val point: Int) : MyInfoSideEffect
}
