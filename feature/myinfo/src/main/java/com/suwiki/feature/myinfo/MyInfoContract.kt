package com.suwiki.feature.myinfo

data class MyInfoState(
  val isLoggedIn: Boolean = false,
)

sealed interface MyInfoSideEffect {
  data object NavigateNotice : MyInfoSideEffect
}
