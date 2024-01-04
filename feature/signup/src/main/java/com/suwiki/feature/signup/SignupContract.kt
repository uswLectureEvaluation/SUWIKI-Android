package com.suwiki.feature.signup

data class SignupState(
  val isLoading: Boolean = false,
)

sealed interface SignupSideEffect {
  data class HandleException(val throwable: Throwable) : SignupSideEffect
  data object PopBackStack : SignupSideEffect
  data object NavigateLogin : SignupSideEffect
}
