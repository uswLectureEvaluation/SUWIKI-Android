package com.suwiki.feature.login

data class LoginState(
  val showEmailNotAuthDialog: Boolean = false,
  val showLoginFailDialog: Boolean = false,
  val id: String = "",
  val password: String = "",
  val showPassword: Boolean = false,
  val isLoading: Boolean = false,
) {
  val loginButtonEnable = id.isNotBlank() && password.isNotBlank()
}

sealed interface LoginSideEffect {
  data class HandleException(val throwable: Throwable) : LoginSideEffect
  data object PopBackStack : LoginSideEffect
  data object NavigateFindId : LoginSideEffect
  data object NavigateFindPassword : LoginSideEffect
  data object NavigateSignUp : LoginSideEffect
}
