package com.suwiki.feature.myinfo

data class MyInfoState(
  val showMyInfoCard: Boolean = false,
  val isLoading: Boolean = false,
  val loginId: String = "",
  val point: Int = 0,
)

sealed interface MyInfoSideEffect {
  data object NavigateNotice : MyInfoSideEffect
  data object NavigateMyEvaluation : MyInfoSideEffect
  data object NavigateMyAccount : MyInfoSideEffect
  data object NavigateMyPoint : MyInfoSideEffect
  data object NavigateBanHistory : MyInfoSideEffect
  data object ShowNeedLoginToast : MyInfoSideEffect
  data object ShowOpenLicenses : MyInfoSideEffect
  data class HandleException(val throwable: Throwable) : MyInfoSideEffect
}
