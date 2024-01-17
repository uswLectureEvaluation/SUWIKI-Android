package com.suwiki.feature.myinfo.quit

data class QuitState(
  val isLoading: Boolean = false,
  val id: String = "",
  val password: String = "",
  val showPassword: Boolean = false,
) {
  val quitButtonEnable = id.isNotBlank() && password.isNotBlank()
}

sealed interface QuitSideEffect {
  data object ShowSuccessQuitToast : QuitSideEffect
  data object PopBackStack : QuitSideEffect
  data class HandleException(val throwable: Throwable) : QuitSideEffect
}
