package com.suwiki.feature.myinfo.myaccount

data class MyAccountState(
  val isLoading: Boolean = false,
  val userId: String = "",
  val userEmail: String = "",
)

sealed interface MyAccountSideEffect {
  data object PopBackStack : MyAccountSideEffect
  data object NavigateResetPassword : MyAccountSideEffect
  data object NavigateQuit : MyAccountSideEffect
  data class HandleException(val throwable: Throwable) : MyAccountSideEffect
}
