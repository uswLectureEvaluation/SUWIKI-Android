package com.suwiki.feature.login.findid

data class FindIdState(
  val email: String = "",
  val isLoading: Boolean = false,
)

sealed interface FindIdSideEffect {
  data class HandleException(val throwable: Throwable) : FindIdSideEffect
  data object PopBackStack : FindIdSideEffect
}
